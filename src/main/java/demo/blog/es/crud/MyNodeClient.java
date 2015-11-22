package demo.blog.es.crud;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.node.Node;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * ʹ��{@link NodeClient}��Ϊ����ES API��Client����ʹ��{@link #getEsClient()}�������س�ʼ����ɵĶ���
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-10-7����09:33:23
 */
public class MyNodeClient implements MyEsClient {
	
	private Client nodeClient;
	
	/**
	 * ���������������ڵ���Ϊ��elasticsearch����Ⱥ�Ľڵ㣬Ȼ�������ڵ㡣�ýڵ���Ա������ݣ��������ݿ��Ա�������
	 */
	public MyNodeClient() {
		
		// ����һ�����ؽڵ㣬�����������ڵ�ES��Ⱥ
		Node node = nodeBuilder()
					.clusterName("elasticsearch") // Ҫ����ļ�Ⱥ��Ϊelasticsearch
					.data(true) // ��Ƕ��ʽ�ڵ���Ա�������
					.node(); // �������������ڵ�
		
		// ���һ��Client���󣬸ö�����Զ������ڵġ�elasticsearch����Ⱥ������ز�����
		nodeClient = node.client();
	}

	public Client getEsClient() {
		return nodeClient;
	}

	public void close() {
		nodeClient.close();
	}

	
}
