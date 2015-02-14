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
			<h2 class="mt2"><span>今日要闻</span></h2>
			<h2 class="min_t"><marquee><#if topNews??><a href="${base}/front/article.do?articleId=${topNews.articleId}">${topNews.articleTitle}</a><#else>&nbsp;</#if></marquee></h2>
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
                        <li title="${latestArticle.articleTitle}"><span class="date">${latestArticle.createByTime}</span><a href="${base}/front/article.do?articleId=${latestArticle.articleId}">${latestArticle.articleTitle}</a></li>
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
        <div class="m_left">
            <div class="oa"><a href="http://10.80.1.225:9080/oa/oa/index.jsp"><img src="${base}/images/oa.jpg" /></a></div>
            <div class="m_tab">
                <div class="m_tab1 mr_10">
                    <script>
<!--
function setTab(name,cursel,n){
 for(i=1;i<=n;i++){
  var menu=document.getElementById(name+i);
  var con=document.getElementById("con_"+name+"_"+i);
  menu.className=i==cursel?"hover":"";
  con.style.display=i==cursel?"block":"none";
 }
}
//-->
                    </script>
                    <div id="lib_Tab1">
                        <div class="lib_Menubox lib_tabborder">
                            <ul>
                                <li id="one1" onclick="setTab('one',1,4)" class="hover">上级文件</li>
                                <li id="one2" onclick="setTab('one',2,4)">分局文件</li>
                               
                            </ul>
                        </div>
                        <div class="lib_Contentbox lib_tabborder">
                            <div id="con_one_1">
                            <ul class="mnlist">
                                <#list superiorFileList as superiorFile>
                                <li title="${superiorFile.articleTitle}"><a href="${base}/front/article.do?articleId=${superiorFile.articleId}">${superiorFile.articleTitle}</a></li>
                                </#list>
                            </ul>
                            <h2 class="tab_more"><a href="${base}/front/articleList.do?type=SUPERIORFILE">更多>></a></h2>
                            </div>
                            <div id="con_one_2" style="display:none">
                                <ul class="mnlist">
                                	<#list branchFileList as branchFile>
                                	<li title="${branchFile.articleTitle}"><a href="${base}/front/article.do?articleId=${branchFile.articleId}">${branchFile.articleTitle}</a></li>
                                	</#list>
                                </ul>
                                <h2 class="tab_more"><a href="${base}/front/articleList.do?type=BRANCHFILE">更多>></a></h2>
                            </div>
                          
                        </div>
                    </div>

                    

                </div>
                <div class="m_tab1">
                    <div id="lib_Tab2">
                        <div class="lib_Menubox lib_tabborder">
                            <ul>
                                <li id="two1" onclick="setTab('two',1,4)" class="hover">通知通报</li>
                                <li id="two2" onclick="setTab('two',2,4)" >昨日要情</li>
                               
                            </ul>
                        </div>
                        <div class="lib_Contentbox lib_tabborder">
                            <div id="con_two_1">
                                <ul class="mnlist">
                                	<#list noticeList as notice>
                                	<li title="${notice.articleTitle}"><a href="${base}/front/article.do?articleId=${notice.articleId}">${notice.articleTitle}</a></li>
                                	</#list>
                                </ul>
                                <h2 class="tab_more"><a href="${base}/front/articleList.do?type=NOTICE">更多>></a></h2>
                            </div>
                            <div id="con_two_2" style="display:none">
                                <ul class="mnlist">
                                	<#list issueWordList as issueWord>
                                    <li><a href="${base}${issueWord.filePath}">${issueWord.issueDate}</a></li>
                                    </#list>
                                </ul>
                                <h2 class="tab_more"><a href="${base}/front/articleList.do?type=ISSUEWORD">更多>></a></h2>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="m_tab1 mr_10">
                    <div id="lib_Tab3">
                        <div class="lib_Menubox lib_tabborder">
                            <ul>
                                <li id="three1" onclick="setTab('three', 1, 4)" class="hover">工作简报</li>
                                <li id="three2" onclick="setTab('three', 2, 4)" >政工简报</li>
                            </ul>
                        </div>
                        <div class="lib_Contentbox lib_tabborder">
                            <div id="con_three_1">
                                <ul class="mnlist">
                                	<#list workReportList as workReport>
                                	<li title="${workReport.articleTitle}"><a href="${base}/front/article.do?articleId=${workReport.articleId}">${workReport.articleTitle}</a></li>
                                	</#list>
                                </ul>
                                <h2 class="tab_more"><a href="${base}/front/articleList.do?type=WORKREPORT">更多>></a></h2>
                            </div>
                            <div id="con_three_2" style="display:none">
                                <ul class="mnlist">
                                	<#list politicalReportList as politicalReport>
                                	<li title="${politicalReport.articleTitle}"><a href="${base}/front/article.do?articleId=${politicalReport.articleId}">${politicalReport.articleTitle}</a></li>
                                	</#list>
                                </ul>
                                <h2 class="tab_more"><a href="${base}/front/articleList.do?type=POLITICALREPORT">更多>></a></h2>
                            </div>
                          
                        </div>
                    </div>
                </div>
                <div class="m_tab1">
                    <div id="lib_Tab4">
                        <div class="lib_Menubox lib_tabborder">
                            <ul>
                                <li id="four1" onclick="setTab('four', 1, 4)" class="hover">公安法制</li>
                                <li id="four2" onclick="setTab('four', 2, 4)" >纪检监察</li>
                            </ul>
                        </div>
                        <div class="lib_Contentbox lib_tabborder">
                            <div id="con_four_1">
                                <ul class="mnlist">
                                	<#list legalList as legal>
                                	<li title="${legal.articleTitle}"><a href="${base}/front/article.do?articleId=${legal.articleId}">${legal.articleTitle}</a></li>
                                	</#list>
                                </ul>
                                <h2 class="tab_more"><a href="${base}/front/articleList.do?type=LEGAL">更多>></a></h2>
                            </div>
                            <div id="con_four_2" style="display:none">
                                <ul class="mnlist">
                                	<#list disciplineList as discipline>
                                	<li title="${discipline.articleTitle}"><a href="${base}/front/article.do?articleId=${discipline.articleId}">${discipline.articleTitle}</a></li>
                                	</#list>
                                </ul>
                                <h2 class="tab_more"><a href="${base}/front/articleList.do?type=DISCIPLINE">更多>></a></h2>
                            </div>
                            
                        </div>
                    </div>
                </div>
                <div class="m_tab1 mr_10">
                    <div id="lib_Tab5">
                        <div class="lib_Menubox lib_tabborder">
                            <ul>
                                <li id="five1" onclick="setTab('five', 1, 4)">警情研判</li>
                                <li id="five2" onclick="setTab('five', 2, 4)" class="hover">警营文化</li>
                                
                            </ul>
                        </div>
                        <div class="lib_Contentbox lib_tabborder">
                            <div id="con_five_1">
                                <ul class="mnlist">
                                	<#list policeCaseList as policeCase>
                                	<li title="${policeCase.articleTitle}"><a href="${base}/front/article.do?articleId=${policeCase.articleId}">${policeCase.articleTitle}</a></li>
                                	</#list>
                                </ul>
                                <h2 class="tab_more"><a href="${base}/front/articleList.do?type=POLICECASE">更多>></a></h2>
                            </div>
                            <div id="con_five_2" style="display:none">
                                <ul class="mnlist">
                                	<#list policeCultureList as policeCulture>
                                	<li title="${policeCulture.articleTitle}"><a href="${base}/front/article.do?articleId=${policeCulture.articleId}">${policeCulture.articleTitle}</a></li>
                                	</#list>
                                </ul>
                                <h2 class="tab_more"><a href="${base}/front/articleList.do?type=POLICECULTURE">更多>></a></h2>
                            </div>
                           
                        </div>
                    </div>
                </div>
                <div class="m_tab1">
                    <div id="lib_Tab6">
                        <div class="lib_Menubox lib_tabborder">
                            <ul>
                                <li id="six1" onclick="setTab('six', 1, 4)">综合考评</li>
                                <li id="six2" onclick="setTab('six', 2, 4)" class="hover">经验交流</li>
                               
                            </ul>
                        </div>
                        <div class="lib_Contentbox lib_tabborder">
                            <div id="con_six_1">
                                <ul class="mnlist">
                                    <#list evaluationList as evaluation>
                                	<li title="${evaluation.articleTitle}"><a href="${base}/front/article.do?articleId=${evaluation.articleId}">${evaluation.articleTitle}</a></li>
                                	</#list>
                                </ul>
                                <h2 class="tab_more"><a href="${base}/front/articleList.do?type=EVALUATION">更多>></a></h2>
                            </div>
                            <div id="con_six_2" style="display:none">
                                <ul class="mnlist">
                                	<#list experienceList as experience>
                                	<li title="${experience.articleTitle}"><a href="${base}/front/article.do?articleId=${experience.articleId}">${experience.articleTitle}</a></li>
                                	</#list>
                                </ul>
                                <h2 class="tab_more"><a href="${base}/front/articleList.do?type=EXPERIENCE">更多>></a></h2>
                            </div>
                            
                        </div>
                    </div>
                </div>
            </div>
			<@p.asLink qgkList=qgkList stkList=stkList sjkList=sjkList />
            <div class="em"><a href="${base}/front/mailbox.do"><img src="${base}/images/e.jpg" /></a></div>
            <@p.login/>          
        </div>
        <!--left end-->
        <div class="m_right">       
               <h2 class="mt5">分局大事记</h2>
            <div class="fjdsjc">
                <marquee id="b"  style="FONT-SIZE: 9pt;  width: 251px; height: 167px; "
                          scrollamount="2" direction="up" onmouseover="this.stop()" onmouseout="this.start()">
                     <#list memorabiliaIndexList as memorabiliaIndex>
                    <h2 style="text-align:center;"><span class="f_blue">${memorabiliaIndex.memorabiliaContent}</span></h2>
                     </#list>
                </marquee></div>
                <h2 class="mt5">站点导航</h2>
            <ul class="zdlist">
                <li><a href="${base}/front/department.do?departmentCode=BGS"><img src="${base}/images/m_1.gif" /></a></li>
                <li><a href="${base}/front/department.do?departmentCode=ZGJJC"><img src="${base}/images/m_2.gif" /></a></li>
                <li><a href="${base}/front/department.do?departmentCode=ZHZX"><img src="${base}/images/m_3.gif" /></a></li>
                <li><a href="${base}/front/department.do?departmentCode=XFK"><img src="${base}/images/m_4.gif" /></a></li>
                <li><a href="http://10.80.48.78:8080/police/login_view.action"><img src="${base}/images/m_5.gif" /></a></li>
                <li><a href="${base}/front/department.do?departmentCode=ZADD"><img src="${base}/images/m_6.gif" /></a></li>
                <li><a href="${base}/front/department.do?departmentCode=XTJDD"><img src="${base}/images/m_7.gif" /></a></li>
                <li><a href="${base}/front/department.do?departmentCode=GBDD"><img src="${base}/images/m_8.gif" /></a></li>
                <li><a href="${base}/front/department.do?departmentCode=SYZDD"><img src="${base}/images/m_9.gif" /></a></li>
                <li><a href="${base}/front/department.do?departmentCode=WADD"><img src="${base}/images/m_10.gif" /></a></li>
                <li><a href="${base}/front/department.do?departmentCode=FZDD"><img src="${base}/images/m_11.gif" /></a></li>
                <li><a href="${base}/front/department.do?departmentCode=BFDD"><img src="${base}/images/m_12.gif" /></a></li>
            </ul>
            <@p.dhLink qgdhList=qgdhList sndhList=sndhList sjdhList=sjdhList sjzsbmdhList=sjzsbmdhList qxfjdhList=qxfjdhList />
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
</body>
<@p.frontJS />
</@p.page>