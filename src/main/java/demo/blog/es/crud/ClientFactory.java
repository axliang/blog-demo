/**
 * 
 */
package demo.blog.es.crud;

import static org.elasticsearch.common.settings.ImmutableSettings.settingsBuilder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.node.Node;

/**
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-10-11����11:01:52
 */
public class ClientFactory {

	public static Client nodeClient() {
		// ����һ�����ؽڵ㣬�����������ڵ�ES��Ⱥ
		Node node = nodeBuilder()
					.clusterName("elasticsearch") // Ҫ����ļ�Ⱥ��Ϊelasticsearch
					.data(true) // ��Ƕ��ʽ�ڵ���Ա�������
					.node(); // �������������ڵ�
		
		// ���һ��Client���󣬸ö�����Զ������ڵġ�elasticsearch����Ⱥ������ز�����
		return node.client();
	}
	
	public static Client transportClient() {
		// ������Ϣ
		Settings esSetting = settingsBuilder()
								.put("cluster.name", "elasticsearch")
								.build();
		TransportClient transportClient = new TransportClient(esSetting);
		
		// ������ӵ�ַ
		TransportAddress address = new InetSocketTransportAddress("127.0.0.1", 9300);
		transportClient.addTransportAddress(address);
		
		return transportClient;
	}
}
