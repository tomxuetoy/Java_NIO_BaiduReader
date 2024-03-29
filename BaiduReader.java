// the original code is from http://alicsd.iteye.com/blog/834447
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.net.InetSocketAddress;
import java.io.IOException;

public class BaiduReader {

	private Charset charset = Charset.forName("GBK");// 创建GBK字符集
	private SocketChannel channel;

	public void readHTMLContent() {
		try {
			InetSocketAddress socketAddress = new InetSocketAddress(
				"www.baidu.com", 80);
			//step1:打开连接
			channel = SocketChannel.open(socketAddress);
			//step2:发送请求，使用GBK编码
			channel.write(charset.encode("GET " + "/ HTTP/1.1" + "\r\n\r\n"));
			//step3:读取数据
			ByteBuffer buffer = ByteBuffer.allocate(1024);// 创建1024字节的缓冲
			while (channel.read(buffer) != -1) {	// try to read all the data from the channel to the buffer
				// flip方法在读缓冲区字节操作之前调用。
				buffer.flip();
				// 使用Charset.decode方法将字节转换为字符串
				System.out.println(charset.decode(buffer));
				System.out.println("-----------------------------------------------");	// for debug
				// 清空缓冲
				buffer.clear();
			}
		} catch (IOException e) {
			System.err.println(e.toString());
		} finally {
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static void main(String[] args) {
		new BaiduReader().readHTMLContent();
	}
}
