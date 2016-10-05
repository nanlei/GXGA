<#assign base="${springMacroRequestContext.getContextPath()}" />
<@p.page>
<@p.head/>
<body>
	<div class="topbg">
		<!--top end-->
        <@p.banner/>
        <!--banner end-->
		<@p.nav/>
	</div>
	<!--topbg end-->
	<div class="mc">
		<div class="m_left">
			<div class="m_od">
				<h2 class="mt1"><span class="f_r">详情</span>值班安排</h2>
				<h2 class="m_txt1"><span class="f_r"><a href="${base}/front/duty.do">查看详情</a></span>${ftlUtil.getToday()}</h2>
				<div class="mod_c">
					<#assign dutyList=ftlUtil.getDutyForIndex()/>
					<#list dutyList as duty>
					<#if (duty_index<2)>
					<h2 class="mod_t">${duty.dutyDate}</h2>
					<ul>
						<li><span>局领导</span>${duty.dutyManager}</li>
						<li><span>值班长</span>${duty.dutyLeader}</li>
						<li><span>值班民警</span>${duty.dutyPolice}</li>
					</ul>
					</#if>
					</#list>
					<#assign dayOfWeek=ftlUtil.getDayOfWeek()/>
	                <#assign dow=''/>
	                <#if dayOfWeek=1><#assign dow='星期日'/>
	                <#elseif dayOfWeek=2><#assign dow='星期一'/>
	                <#elseif dayOfWeek=3><#assign dow='星期二'/>
	                <#elseif dayOfWeek=4><#assign dow='星期三'/>
	                <#elseif dayOfWeek=5><#assign dow='星期四'/>
	                <#elseif dayOfWeek=6><#assign dow='星期五'/>
	                <#elseif dayOfWeek=7><#assign dow='星期六'/>
	                </#if>
					<h2 class="m_date">现在是：${ftlUtil.getToday()} ${dow}</h2>
				</div>
			</div>
		<div class="m_in">
			<h2 class="mt2"><a href="${base}/front/articleList.do?type=IMAGENEWS">更多>></a><span>今日要闻</span></h2>
			<h2 class="min_t"><marquee scrollamount="4"><#if topNews??><a href="${base}/front/article.do?articleId=${topNews.articleId}">${topNews.articleTitle}</a><#else>&nbsp;</#if></marquee></h2>
			<div class="min_left">
				<div id="banner">
					<div id="f_div">
					<!--图片区域-->
					<div style="width: 285px; height: 245px" id="f_imgdiv"></div>
					<div id="f_infodiv">
						<!--数字按钮区域-->
						<div id="f_buttondiv"></div>
					</div>
				</div>
			</div>
			<script>
				//可修改区域
				var imgwidth = 285;//宽
				var imgheight = 245;//高
				var _timeOut_ = 4000;//过渡时间
				var show_text = true; //是否显示焦点文字
				var timeOut = _timeOut_;
				var timeOut2 = _timeOut_ / 2;//onmouseout img后需要切换的时间
				var now = 0;//第一张图
				var target = "_blank";//打开方式
				var button_on = 'on'; //当前焦点对应按钮的样式名
				var button_off = '';//非当前焦点对应按钮的样式名
				//不可修改区域
				var imgUrl = new Array();
				var imgText = new Array();
				var imgLink = new Array();
				var imgAlt = new Array();
				//var menuList = new Array();//菜单menu
				var ver = 2; //兼容浏览器版本 默认2 为非ie
				var firstTime = true;
				var n = -1;
				
				<#if imageNewsList??>
				<#list imageNewsList as imageNews>
				<#if (imageNews_index<=4)>
				imgUrl[++n] = '${base}${imageNews.imageUrl}';
				imgText[n] = '<a href="${base}/front/article.do?articleId=${imageNews.articleId}" class=linkblack>${imageNews.articleTitle}</a>';
				imgLink[n] = '${base}/front/article.do?articleId=${imageNews.articleId}';
				imgAlt[n] = '${imageNews.articleTitle}';
				</#if>
				</#list>
				</#if>
				
				var count = 0;
				for (i = 0; i < imgUrl.length; i++) {
					if ((imgUrl[i] != "") && (imgText[i] != "") && (imgLink[i] != "") && (imgAlt[i] != "")) {
						count++;
					} else {
						break;
					}
				}
				
				function p$(string) {
					document.write(string);
				}
                    function $$(id) {
                        return document.getElementById(id);
                    }
                    //固定图片size
                    p$("<style> #f_img { width:" + imgwidth + "px;height:" + imgheight + "px;</style>");

                    function change() {
                        if (ver == 1) {
                            with ($$('f_img').filters[0]) {
                                Transition = 1;
                                apply();
                                play();
                            }
                        }
                        if (firstTime) { firstTime = false; timeOut = _timeOut_ / 1000; }
                        else {
                            $$('f_img').src = imgUrl[now];
                            $$('f_img').alt = imgAlt[now];
                            $$('f_imgLink').href = imgLink[now];
                            for (var i = 0; i < count; i++) {
                                $$('b' + i).className = "button";
                                //$$('f_menu'+i).className="";
                            }
                            $$('b' + now).className = "on";
                            //$$('f_menu'+now).className="on";
                            now = (now >= imgUrl.length - 1) ? 0 : now + 1;
                            timeOut = _timeOut_;
                        }
                        theTimer = setTimeout("change()", timeOut);
                    }
                    function b_change(num) {
                        window.clearInterval(theTimer);
                        now = num;
                        firstTime = false;
                        change();
                    }
                    //draw 渐变line （即css:f_line)
                    function draw_line() {
                        var div = document.createElement("div");
                        div.id = 'f_line';
                        $$('f_infodiv').insertBefore(div, $$('f_infodiv').childNodes.item(0));
                    }
                    //表现层 start
                    //图片
                    var a = document.createElement("a");
                    a.id = "f_imgLink";
                    //a.target=target;
                    a.href = imgLink[now];
                    $$('f_imgdiv').appendChild(a);

                    var img = document.createElement("img");
                    img.id = "f_img";
                    img.width = imgwidth;
                    img.height = imgheight;
                    img.src = imgUrl[now];
                    img.alt = imgAlt[now];
                    a.appendChild(img);

                    //数字按钮
                    for (var i = count - 1; i >= 0; i--) {
                        var div_bg = document.createElement("div");
                        div_bg.id = 'div_bg' + i;
                        div_bg.className = 'bg';
                        $$('f_buttondiv').appendChild(div_bg);
                        var a = document.createElement("a");
                        a.id = 'b' + i;
                        a.className = (i == now + 1) ? "button_on" : "button_off";
                        a.title = imgAlt[i];
                        a.innerHTML = i + 1;
                        a.href = 'javascript:b_change(' + i + ')';
                        $$('div_bg' + i).appendChild(a);
                        var div = document.createElement("div");
                        $$('f_buttondiv').appendChild(div);
                    }
                    //表现层 end
                    $$('f_img').onmouseover = function () { window.clearInterval(theTimer); }
                    $$('f_img').onmouseout = function () { theTimer = setTimeout("change()", timeOut2); }
                    try {    //滤镜版本
                        new ActiveXObject("DXImageTransform.Microsoft.Fade");
                        $$('f_img').filters[0].play();
                        ver = 1;
                        draw_line();
                    }
                    catch (e) { ver = 2; }
                    var theTimer = setTimeout("change()", _timeOut_ / 1000);
                </script>
            </div>
            <div class="min_right">
            	<#if imageNewsList??>
            	<#list imageNewsList as imageNews>
            	<#if (imageNews_index<8)>
            	<h2 title="${imageNews.articleTitle}"><a href="${base}/front/article.do?articleId=${imageNews.articleId}">${imageNews.articleTitle}</a></h2>
            	</#if>
            	</#list>
            	</#if>
            </div>
            </div>
        <div class="m_contribute">
            <h2 class="mt3">投稿统计</h2>
            <ul>
                <li class="li2"><span class="s1">名次</span><span class="s2">单位</span><span class="s1">数量</span></li></ul>
                <marquee id="b" onmouseover="this.stop()" style=" width: 200px; height: 174px; background: #E9F4E1;"
                         onmouseout="this.start()" scrollamount="2" direction="up">
                    <ul>
                       	<#list rankList as rank>
                       	<li><span class="s1">${rank_index+1}</span><span class="s2">${rank.departmentName}</span><span class="s1">${rank.rankValue}</span></li>
                       	</#list>
                    </ul>
                </marquee>
            </ul></div> 
            <div class="searchbox">
                <h2><form action="${base}/front/search.do" method="post"><input id="Text1" type="text" name="searchString" class="txtbox1"/><input id="Button1" type="submit" value="搜索" class="btn1"/></form></h2>
                <h2 class="search_2"><a href="">大连市公安局</a>|<a href="">大连公安</a>|<a href="">大连市</a></h2>
            </div>
            <div class="m_ad1">
                <img src="images/add1.jpg" /></div>
        </div>
        <!--left end-->
        <div class="m_right">
            <div class="tlr">
                <h2 class="mt4"><a href="${base}/front/latest.do">更多>></a><span>最近发布</span></h2>
                <marquee id="b" onmouseover="this.stop()" style=" width: 251px; height: 533px; " onmouseout="this.start()" scrollamount="2" direction="up">
                    <ul class="news">
                    	<#list latestArticleList as latestArticle>
                        <li title="${latestArticle.articleTitle}"><span class="date"><#if latestArticle.articleDate??>${latestArticle.articleDate}<#else>${latestArticle.createByTime}</#if></span><a href="${base}/front/article.do?articleId=${latestArticle.articleId}">${latestArticle.articleTitle}</a></li>
                        </#list>
                    </ul>
                </marquee>
            </div>
        </div>
        <!--right end-->        
        <h2 class="mad2">
            <img src="images/add2.jpg" />
        </h2>
        <!--add end-->
 
        <!-- m_left start -->
		<div class="m_left">
			<div class="con1 mr_15 mb_20">
				<h2 class="mt4"><a href="${base}/front/articleList.do?type=NOTICE">更多>></a><span>通知通报</span></h2>
				<ul class="mnlist">
				    <#list noticeList as notice>
                    <#if notice.articleBizType="NOR">
                    <li title="${notice.articleTitle}"><a href="${base}/front/article.do?articleId=${notice.articleId}">${notice.articleTitle}</a>${notice.articleDate}</li>
                    <#elseif notice.articleBizType="RED">
                    <li title="${notice.articleTitle}"><a href="${base}/front/articleRedHead.do?articleId=${notice.articleId}">${notice.articleTitle}</a>${notice.articleDate}</li>
                    </#if>
                    </#list>
				</ul>
			</div>
			<div class="con1 mb_20">
				<h2 class="mt4"><a href="${base}/front/articleList.do?type=BRANCHFILE">更多>></a><span>分局文件</span></h2>
				<ul class="mnlist">
                    <#list branchFileList as branchFile>
                    <#if branchFile.articleBizType="NOR">
                    <li title="${branchFile.articleTitle}"><a href="${base}/front/article.do?articleId=${branchFile.articleId}">${branchFile.articleTitle}</a>${branchFile.articleDate}</li>
                    <#elseif branchFile.articleBizType="RED">
                    <li title="${branchFile.articleTitle}"><a href="${base}/front/articleRedHead.do?articleId=${branchFile.articleId}">${branchFile.articleTitle}</a>${branchFile.articleDate}</li>
                    </#if>
                    </#list>
				</ul>
			</div>
			<div class="con1 mr_15 mb_20">
				<h2 class="mt4"><a href="${base}/front/articleList.do?type=WORKREPORT">更多>></a><span>工作动态</span></h2>
				<ul class="mnlist">
                	<#list workReportList as workReport><#-- 工作动态(工作简报) -->
                    <li title="${workReport.articleTitle}"><a href="${base}/front/article.do?articleId=${workReport.articleId}">${workReport.articleTitle}</a>${workReport.articleDate}</li>
                    </#list>
				</ul>
			</div>
			<div class="con1 mb_20">
				<h2 class="mt4"><a href="${base}/front/articleList.do?type=POLICECASE">更多>></a><span>每日警情</span></h2>
				<ul class="mnlist">
                	<#list policeCaseList as policeCase><#-- 每日警情(警情研判) -->
                    <#if policeCase.type=='ARTICLE'>
                    <li title="${policeCase.articleTitle}"><a href="${base}/front/article.do?articleId=${policeCase.articleId}">${policeCase.articleTitle}</a>${policeCase.articleDate}</li>
                    <#elseif policeCase.type='WORD'>
                    <li title="${policeCase.articleTitle}"><a href="${base}${policeCase.filePath}">${policeCase.articleTitle}</a>${policeCase.articleDate}</li>
                    </#if>
                    </#list>
				</ul>
			</div>
			<div class="con1 mr_15 mb_20">
				<h2 class="mt4"><a href="${base}/front/articleList.do?type=POLITICALNOTICE">更多>></a><span>政工通知</span></h2>
				<ul class="mnlist">
                	<#list politicalNoticeList as politicalNotice>
                    <li title="${politicalNotice.articleTitle}"><a href="${base}/front/article.do?articleId=${politicalNotice.articleId}">${politicalNotice.articleTitle}</a>${politicalNotice.articleDate}</li>
                    </#list>
				</ul>
			</div>
			<div class="con1 mb_20">
				<h2 class="mt4"><a href="${base}/front/articleList.do?type=POLITICALREPORT">更多>></a><span>政工简报</span></h2>
				<ul class="mnlist">
                	<#list politicalReportList as politicalReport>
                    <li title="${politicalReport.articleTitle}"><a href="${base}/front/article.do?articleId=${politicalReport.articleId}">${politicalReport.articleTitle}</a>${politicalReport.articleDate}</li>
                    </#list>
				</ul>
			</div>
			<div class="con1 mr_15">
				<h2 class="mt4"><a href="${base}/front/articleList.do?type=EVALUATION">更多>></a><span>督导考核</span></h2>
				<ul class="mnlist">
                	<#list evaluationList as evaluation><#-- 督导考核(综合考评) -->
                    <li title="${evaluation.articleTitle}"><a href="${base}/front/article.do?articleId=${evaluation.articleId}">${evaluation.articleTitle}</a>${evaluation.articleDate}</li>
                    </#list>
				</ul>
			</div>
			<div class="con1">
				<h2 class="mt4"><a href="${base}/front/articleList.do?type=LEGAL">更多>></a><span>公安法制</span></h2>
				<ul class="mnlist">
					<#list legalList as legal>
                    <li title="${legal.articleTitle}"><a href="${base}/front/article.do?articleId=${legal.articleId}">${legal.articleTitle}</a>${legal.articleDate}</li>
                    </#list>
				</ul>
			</div> 
		</div>
        <!--left end-->
        <div class="m_right">       
			<@p.monthlyStar monthlyStar??/>
			<@p.indexMailbox />
			<@p.zdList />
            <@p.dhLink qgdhList=qgdhList sndhList=sndhList sjdhList=sjdhList sjzsbmdhList=sjzsbmdhList qxfjdhList=qxfjdhList />
            <@p.asLink qgkList=qgkList stkList=stkList sjkList=sjkList />
        </div>
        <!--right end-->

        <!--case end-->
   	<@p.topic/>
    <@p.birthday birthdayList/>
    <@p.emergencyNotice emergencyNoticeList/>       
            
    </div>
   
    <!--beginning of bottom -->
    <@p.bottom/>
    <!--end of bottom -->
	<script src="${base}/js/scroll.js"></script>
	<script src="${base}/js/scrolltopcontrol.js"></script>
</body>
<@p.frontJS />
</@p.page>