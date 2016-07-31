package sam.bee.stock.loader.impl;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Vector;

public abstract class BaseLoader {

	//	protected List<String> getResponseBlock(String request) throws IOException {
//		HttpURLConnection conn = null;
//		InputStream in = null;
//		BufferedReader br = null;
//		URL url = new URL(request);
//		List<String> list = new ArrayList<String>(0);
//		try {
//			conn = (HttpURLConnection) url.openConnection();
//			conn.setConnectTimeout(10000);
//			in = conn.getInputStream();
//			br = new BufferedReader(new InputStreamReader(in, "GBK"));
//			String tmp = null;
//
//			while (null != (tmp = br.readLine())) {
//				list.add(tmp);
//			}
//
//		} finally {
//			if (br != null) {
//				try {
//					br.close();
//				} catch (Exception e) {
//				}
//			}
//			if (in != null) {
//				try {
//					in.close();
//				} catch (Exception e) {
//				}
//			}
//			if (conn != null) {
//				try {
//					conn.getInputStream().close();
//				} catch (Exception e) {
//				}
//			}
//		}
//		return list;
//	}
//
//	private static Charset utf8 = Charset.forName("UTF8");// 创建GBK字符集
//	private static Charset gbk = Charset.forName("GBK");
//	public List<String> getResponseNIO(String request) throws IOException, URISyntaxException {
//		URI uri = new URI(request);
//		String scheme = uri.getScheme();
//		if (scheme == null || !scheme.equals("http"))
//			throw new IllegalArgumentException("Must use 'http:' protocol");
//
//		int port =uri.getPort()==-1 ? 80: uri.getPort();
//
//		Charset charset = gbk;
//		SocketChannel channel = SocketChannel.open(new InetSocketAddress(uri.getHost(), port));
//		String line = "GET "+uri.getRawPath() +"?" + uri.getRawQuery()+" HTTP/1.1 \r\n";
//		line += "HOST:"+uri.getHost()+"\r\n";
//		line += "\r\n";
//		channel.write(charset.encode(line));
//		ByteBuffer buffer = ByteBuffer.allocate(1024);// 创建1024字节的缓冲
//		int size = channel.read(buffer);
//		String out = "";
//		while (size != -1) {
//			buffer.flip();
//			while (buffer.hasRemaining()) {
//				System.out.print(charset.decode(buffer));
//				System.out.println("------------------------");
//			}
//			buffer.clear();
//			size = channel.read(buffer);
//		}
//		return null;
//	}
	OkHttpClient client = new OkHttpClient();
	public String getResponse(String url) throws IOException, URISyntaxException {
//		String data = new HttpGet().execute(request);
//		System.out.print(data);
		Request request = new Request.Builder()
				.url(url)
				.build();

		Response response = client.newCall(request).execute();
		if(response.isSuccessful()) {
			return response.body().string();
		}
		else{
			response.body().close();
		}
		return null;
	}
}
