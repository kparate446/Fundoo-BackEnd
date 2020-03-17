package com.bridgelabz.fundoonotesapi.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.lucene.search.join.ScoreMode;
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
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotesapi.model.Notes;
import com.bridgelabz.fundoonotesapi.responce.Response;
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
		IndexRequest indexRequest = new IndexRequest(index, type, UUID.randomUUID().toString()).source(mapper);
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
	/** FindById*/
	public Notes findById(String id) throws Exception {
		GetRequest getRequest = new GetRequest(index, type, id);
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		Map<String, Object> resultMap = getResponse.getSource();
		return objectMapper.convertValue(resultMap, Notes.class);
	}
//	//Seach the all data
//	private List<Notes> getSearchResult(SearchResponse response) {
//		SearchHit[] searchHit = response.getHits().getHits();
//		List<Notes> notes = new ArrayList<>();
//		if (searchHit.length > 0) {
//			Arrays.stream(searchHit)
//					.forEach(hit -> notes.add(objectMapper.convertValue(hit.getSourceAsMap(), Notes.class)));
//		}
//		return notes;
//	}
	// update Note using elastic search 
	/** Updated Notes*/
	public void updateNote(Notes noteEntity) throws Exception {
		Notes resultNote = findById(String.valueOf(noteEntity.getId()));
		UpdateRequest updateRequest = new UpdateRequest(index, type, String.valueOf(resultNote.getId()));
		@SuppressWarnings("unchecked")
		Map<String, Object> documentMapper = objectMapper.convertValue(noteEntity, Map.class);
		updateRequest.doc(documentMapper);
		client.update(updateRequest, RequestOptions.DEFAULT);
	}
	/** Search by Discription*/
	@SuppressWarnings("unchecked")
	public List<Notes> searchByDiscription(String title) throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery().must(QueryBuilders.matchQuery("Discription.name", title));
        searchSourceBuilder.query(QueryBuilders
                .nestedQuery("Discription",queryBuilder,ScoreMode.Avg));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        return (List<Notes>) new Response(200, "SearchByDiscription", response);
        
//        return getSearchResult(response);
    }
	/** Search by Title*/
	@SuppressWarnings("unchecked")
	public List<Notes> searchByTitle(String title) throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery().must(QueryBuilders.matchQuery("title.name", title));
        searchSourceBuilder.query(QueryBuilders
                .nestedQuery("title",queryBuilder,ScoreMode.Avg));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        return (List<Notes>) new Response(200, "searchByTitle", response);
    }
	/** Show All Data*/
	@SuppressWarnings("unchecked")
	public List<Notes> findAll() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =
                client.search(searchRequest, RequestOptions.DEFAULT);
        return (List<Notes>) new Response(200, "findAll", searchResponse);


    }
}

