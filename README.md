SmartProxy
==========

安卓下的智能代理 

======

由于原repo很久没有更新，而Shadowsocks的客户端在Lollipop上经常会崩溃，导致上网过程十分不流畅，所以我给SmartProxy添加了Shadowsocks的支持还有其他一些小功能：

1. Shadowsocks支持，使用shadowsocks-libv中的加密方法，理论上支持所有的加密方式
2. 开机启动支持，仅在5.0+系统工作，因为4.4每次都要弹出来VPN的对话框所以没办法，用Xposed里面的VPN auto confirm可能可以工作
3. 多配置文件支持，再也不用每次换配置都扫码了。

因为代码改的有点乱，而且不知道作者还有没有在维护，所以就先没提PR。要使用Shadowsocks的话，可以直接填入ss://的uri或者把pac文件中的proxy填写为ss://的地址都可以。


使用中发现的问题：
 1. 部分请求即便是需要代理的，发送的是ip而不是host。比如google商店下载app时的域名gvt1.com。这会导致代理不能根据域名区分流量。而换用Drony这个app则此请求发送的是host。
 2. 部分请求会被重定向到代理的端口，导致不能请求到数据。使用Debug模式，在log中可见，这个可能是bug.比如原请求是1.2.3.4:80,变成了1.2.3.4:8118.


