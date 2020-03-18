package com.bridgelabz.fundoonotesapi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotesapi.model.Notes;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchServiceImp {

	private RestHighLevelClient client;

	private ObjectMapper objectMapper;

	public ElasticSearchServiceImp(RestHighLevelClient client, ObjectMapper objectMapper) {

		this.client = client;
		this.objectMapper = objectMapper;
	}
	private String index = "elasticsearch";
	private String type = "createnote";
	
	/** Create Note*/
	@SuppressWarnings("unchecked")
	public void createNote(Notes notes) throws IOException {
		Map<String, Object> mapper = objectMapper.convertValue(notes, Map.class);
		IndexRequest indexRequest = new IndexRequest(index, type,  UUID.randomUUID().toString()).source(mapper);
		System.out.println("client request"+client);
		System.out.println("index request"+ indexRequest);
		client.index(indexRequest, RequestOptions.DEFAULT);

	}
	/**delete Note using elastic search*/
	public String deleteNote(String id) throws IOException {
		DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
		DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
		return deleteResponse.getResult().name();
	}

	/** Update Notes*/
	@SuppressWarnings("unchecked")
	public void UpdateNotes(Notes notes) throws Exception {
		Notes resultDocument = findById(notes.getTitle());
		UpdateRequest updateRequest = new UpdateRequest(index, type, String.valueOf(resultDocument.getId()));
		Map<String, Object> documentMapper = objectMapper.convertValue(notes, Map.class);
		updateRequest.doc(documentMapper);
		client.update(updateRequest, RequestOptions.DEFAULT);
	}
	/** FindById*/
	public Notes findById(String id) throws Exception {
		GetRequest getRequest = new GetRequest(index, type, id);
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		Map<String, Object> resultMap = getResponse.getSource();
		return objectMapper.convertValue(resultMap, Notes.class);
	}
	/**Show all notes*/
	public List<Notes> showAllNotes() throws IOException {
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHit[] searchHit = searchResponse.getHits().getHits();
		List<Notes> listOfNote = new ArrayList<Notes>();//ArrayList<NoteEntity>();
		if (searchHit.length > 0) {
			Arrays.stream(searchHit)
			.forEach(hit -> listOfNote.add(objectMapper.convertValue(hit.getSourceAsMap(), Notes.class)));
		}
		return listOfNote;
	}
}

