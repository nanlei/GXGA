<!--  Written by Myhyli, 2003/07/21. myhyli.blueidea.com  -->
<!--  Member Of Blueidea Web Team. -->
<!--  Welcome to www.blueidea.com. -->

	var delta=0.8;
	var collection;
	var closeB=false;
	function floaters() {
		this.items	= [];
		this.addItem	= function(id,x,y,content)
				  {
					document.write('<DIV id='+id+' style="Z-INDEX: 10; POSITION: absolute;  width:80px; height:60px;left:'+(typeof(x)=='string'?eval(x):x)+';top:'+(typeof(y)=='string'?eval(y):y)+'">'+content+'</DIV>');
					
					var newItem				= {};
					newItem.object			= document.getElementById(id);
					newItem.x				= x;
					newItem.y				= y;

					this.items[this.items.length]		= newItem;
				  }
		this.play	= function()
				  {
					collection				= this.items
					setInterval('play()',30);
				  }
		}
		function play()
		{
			if(screen.width<=800 || closeB)
			{
				for(var i=0;i<collection.length;i++)
				{
					collection[i].object.style.display	= 'none';
				}
				return;
			}
			for(var i=0;i<collection.length;i++)
			{
				var followObj		= collection[i].object;
				var followObj_x		= (typeof(collection[i].x)=='string'?eval(collection[i].x):collection[i].x);
				var followObj_y		= (typeof(collection[i].y)=='string'?eval(collection[i].y):collection[i].y);

				if(followObj.offsetLeft!=(document.body.scrollLeft+followObj_x)) {
					var dx=(document.body.scrollLeft+followObj_x-followObj.offsetLeft)*delta;
					dx=(dx>0?1:-1)*Math.ceil(Math.abs(dx));
					followObj.style.left=followObj.offsetLeft+dx;
					}

				if(followObj.offsetTop!=(document.body.scrollTop+followObj_y)) {
					var dy=(document.body.scrollTop+followObj_y-followObj.offsetTop)*delta;
					dy=(dy>0?1:-1)*Math.ceil(Math.abs(dy));
					followObj.style.top=followObj.offsetTop+dy;
					}
				followObj.style.display	= '';
			}
		}	
		function closeBanner()
		{
			closeB=true;
			return;
		}

	var theFloaters		= new floaters();
	//
	theFloaters.addItem('followDiv1','document.body.clientWidth-106',80,'<a href=http://www.divcss5.com/ target=_blank>…˙»’øÏ¿÷</a><br><br><img src=img/close.gif onClick="closeBanner();">');
	theFloaters.addItem('followDiv2',6,80,'<a href=http://www.divcss5.com/ target=_blank><img src=img/ad-01.gif></a><br><br><img src=img/close.gif onClick="closeBanner();">');
	theFloaters.play();
	

//<a href=http://digi.blueidea.com/mms target=_blank><img src=/gg/blueidea/2004/digimms.gif width=100 height=100 border=0></a><a href=/news/game/2004/2363.asp><img src=/gg/computerarts/5000.gif width=100 height=150 border=0></a>