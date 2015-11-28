package demo.blog.es.crud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.min.InternalMin;

/**
 * ��ES����˵�CRUD������
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-10-7����10:33:16
 */
public class EsCrud {
	
	private Client client;
	
	/**
	 * ����һ��ʵ����
	 * @param client 
	 */
	public EsCrud(Client client) {
		this.client = client;
	}
	
	/***************** index ******************************/
	
	/**
	 * ����һ������
	 * @param indexName ������
	 */
	public void createIndex(String indexName) {
		try {
			CreateIndexResponse indexResponse = this.client
									.admin()
									.indices()
									.prepareCreate(indexName)
									.get();
			
			System.out.println(indexResponse.isAcknowledged()); // true��ʾ�����ɹ�
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����������mapping��
	 * @param index ������
	 * @param type mapping����Ӧ��type
	 */
	public void addMapping(String index, String type) {
		try {
			// ʹ��XContentBuilder����Mapping
			XContentBuilder builder = 
				XContentFactory.jsonBuilder()
							    .startObject()
							    	.field("properties")
							    		.startObject()
							    			.field("name")
							    				.startObject()
							    					.field("index", "not_analyzed")
							    					.field("type", "string")
							    				.endObject()
							    			.field("age")
							    				.startObject()
							    					.field("index", "not_analyzed")
							    					.field("type", "integer")
							    				.endObject()
							    		.endObject()
							    .endObject();
			System.out.println(builder.string());			
			PutMappingRequest mappingRequest = Requests.putMappingRequest(index).source(builder).type(type);
					this.client.admin().indices().putMapping(mappingRequest).actionGet();
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ɾ������
	 * @param index Ҫɾ����������
	 */
	public void deleteIndex(String index) {
		DeleteIndexResponse deleteIndexResponse = 
			this.client
				.admin()
				.indices()
				.prepareDelete(index)
				.get();
		System.out.println(deleteIndexResponse.isAcknowledged()); // true��ʾ�ɹ�
	}
	
	/******************** doc *************************************/
	
	/**
	 * ����һ���ĵ�
	 * @param index index
	 * @param type type
	 */
	public void createDoc(String index, String type, String id) {
		
		try {
			// ʹ��XContentBuilder����һ��doc source
			XContentBuilder builder = 
				XContentFactory.jsonBuilder()
								.startObject()
								    .field("name", "zhangsan")
								    .field("age", 12)
								.endObject();
			
			IndexResponse indexResponse = this.client
											.prepareIndex()
											.setIndex(index)
											.setType(type)
											.setId(id) // ���û������id����ES���Զ�����һ��id
											.setSource(builder.string())
											.get();
			System.out.println(indexResponse.isCreated());
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �����ĵ�
	 * @param index
	 * @param type
	 * @param id
	 */
	public void updateDoc(String index, String type, String id) {
		try {
			XContentBuilder builder = 
				XContentFactory.jsonBuilder()
								.startObject()
								    .field("name", "lisi")
								    .field("age", 12)
								.endObject();
			
			UpdateResponse updateResponse = 
				this.client
					.prepareUpdate()
					.setIndex(index)
					.setType(type)
					.setId(id)
					.setDoc(builder.string())
					.get();
			System.out.println(updateResponse.isCreated()); // true��ʾ�ɹ�
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ɾ��һ������
	 * @param index
	 * @param type
	 * @param id
	 */
	public void deleteDoc(String index, String type, String id) {

		DeleteResponse deleteResponse  = this.client
				.prepareDelete()   
				.setIndex(index) 
				.setType(type)
				.setId(id)
				.get();
		System.out.println(deleteResponse.isFound()); // true��ʾ�ɹ�
	}
	
	/**
	 * ���ݲ�ѯ����ɾ���ĵ���
	 */
	public void deleteByQuery(String index, String type) {
		try {
			QueryBuilder queryBuilder = QueryBuilders.termQuery("name", "zhangsan");
			DeleteByQueryResponse deleteByQueryResponse = this.client
					.prepareDeleteByQuery(index)
					.setTypes(type)
					.setQuery(queryBuilder)
					.get();
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ID��ѯһ�����ݼ�¼��
	 * @param id Ҫ��ѯ���ݵ�ID��
	 * @return ���ز�ѯ�����ļ�¼�����json�ַ�����
	 */
	public String get(String index, String type, String id) {
		GetResponse getResponse = this.client
									.prepareGet()   // ׼������get��������ʱ����������ִ��get����������ֱ��get������
									.setIndex(index)  // Ҫ��ѯ��
									.setType(type)
									.setId(id)
									.get();
		return getResponse.getSourceAsString();
	}
	
	/**
	 * ʹ��filter��ʽ��ѯ���ݡ�
	 * @param index �������ڵ�������
	 * @param type �������ڵ�type
	 * @return 
	 */
	public List<String> queryByFilter(String index, String type) {
		
		// ��ѯ��Ϊzhangsan������
		FilterBuilder filterBuilder = FilterBuilders.termFilter("name", "zhangsan");
		SearchResponse searchResponse = 
			this.client
				.prepareSearch()  
				.setIndices(index)
				.setTypes(type)
				.setPostFilter(filterBuilder)
				.get();
		
		List<String> docList = new ArrayList<String>();
		SearchHits searchHits = searchResponse.getHits();
		for (SearchHit hit : searchHits) {
			docList.add(hit.getSourceAsString());
		}
		return docList;
	}
	
	/**
	 * ʹ��min�ۺϲ�ѯĳ���ֶ�����С��ֵ��
	 * @param index
	 * @param type
	 */
	public void min(String index, String type) {
		SearchResponse response = this.client
								.prepareSearch(index)
								.addAggregation(AggregationBuilders.min("min").field("age"))
								.get();
		
		InternalMin min = response.getAggregations().get("min");
		System.out.println(min.getValue());
	}
	
	public static void main(String[] args) {
		XContentBuilder builder = null;
		try {
			builder = XContentFactory.jsonBuilder()
			.startObject()
				.field("properties")
					.startObject()
						.field("name")
							.startObject()
								.field("index", "not_analyzed")
								.field("type", "string")
							.endObject()
					.endObject()
			.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			System.out.println(builder.string());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
