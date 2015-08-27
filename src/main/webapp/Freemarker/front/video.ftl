<#assign base="${springMacroRequestContext.getContextPath()}" />
<@p.page>
<@p.head/>
<body>
    <div class="topbg">
		<!--top end-->
		<@p.banner/>
        <!--banner end-->
        <@p.nav on="index"/>
    </div>
    <!--topbg end-->
    <div class="mc">      
            <div class="location">
                <a href="Default.htm">首页</a> > 视频 > <span>视频</span>
            </div>
           
        <div class="left">
            <h2 class="st"><span>视频</span></h2>
            <div class="sac">
                <h2 class="spt">这是测试视频</h2>
                <h2 class="time">发布时间：2014-12-28</h2>
                <#if UserAgent?index_of('Chrome')!=-1||UserAgent?index_of('Firefox')!=-1>
                <video src="${base}/file/video/video_test.mp4" width="100%" height="100%" autoplay="autoplay" controls="controls">您的浏览器不支持视频播放功能</video>
                <#else>
                <object width="672" height="400" type="video/x-ms-asf" url="${base}/file/video/video_test.mp4" data="${base}/file/video/video_test.mp4" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6">
                    <param name="url" value="${base}/file/video/video_test.mp4" />
                    <param name="filename" value="${base}/file/video/video_test.mp4" />
                    <param name="autostart" value="0" />
                    <param name="ShowControls" value="1" />
                    <param name="WindowlessVideo" value="0" />
                    <param name="uiMode" value="full" />
                    <param name="autosize" value="0" />
                    <param name="playcount" value="1" />
                    <embed type="application/x-mplayer2" src="${base}/file/video/video_test.mp4" width="100%" height="100%" autostart="false" showcontrols="true" pluginspage="http://www.microsoft.com/Windows/MediaPlayer/" /></embed>
                </object>
                </#if>
            </div>
            <div class="page">
                上一篇：<a href="video.htm">视频名称1</a><br />
                下一篇：<a href="video.htm">视频名称3</a>
            </div>
        </div>
        <!--left end-->
        <div class="s_right">
            <h2><img src="${base}/images/srnavt.jpg" /></h2>
            <div class="rnav">
                <h2 class="srnt">
                    视频
                </h2>
                <ul>
                    <li class="on"><a href="video.htm">视频</a></li>
                                 
                </ul>
            </div>
            <h2 class="mb_20" ><img src="${base}/images/srnavb.jpg" /></h2>
            <@p.duty/>
			<@p.rank/>
            <@p.imageLink/>
        </div>
        <!--right end-->
		<@p.topic/>
    </div>
    <!--beginning of bottom -->
	<@p.bottom/>
    <!--end of bottom -->
</body>
</@p.page>