package com.game.gameServer.framework;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * 开始端口监听
 * 
 * @author hjj2019
 *
 */
interface IServer_ListenCSMsg {
	/**
	 * 监听 CS 消息
	 * 
	 */
	public default void listenCSMsg() {
		// 记录异步操作服务初始化日志
		FrameworkLog.LOG.info(":: start PortListen");

		// 创建 IO 接收器
		NioSocketAcceptor acceptor = new NioSocketAcceptor();

		// 获取责任链
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		// 处理网络粘包
		chain.addLast("msgCumulative", new MINA_MsgCumulativeFilter());
		// 消息加密
		chain.addLast("msgDecrypt", new MINA_MsgDecryptFilter());
		// 添加自定义编解码器
		chain.addLast("msgCodec", new ProtocolCodecFilter(new MINA_MsgCodecFactory()));

		// 获取会话配置
		IoSessionConfig cfg = acceptor.getSessionConfig();

		// 设置缓冲区大小
		cfg.setReadBufferSize(4096);
		// 设置 session 空闲时间
		cfg.setIdleTime(IdleStatus.BOTH_IDLE, 10);

 		// 设置 IO 句柄
		acceptor.setHandler(new MINA_IoHandler());
		acceptor.setReuseAddress(true);

		try {
			// 绑定端口
			acceptor.bind(new InetSocketAddress(4400));
		} catch (Exception ex) {
			// 输出异常并停止服务器
			FrameworkLog.LOG.error(ex.getMessage(), ex);
			System.exit(-1);
		}
	}
}
