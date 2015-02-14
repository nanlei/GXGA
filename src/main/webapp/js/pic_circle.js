	$(function(){
		var $ad = $('#abgne_fade_pic a.ad'),
			showIndex = 0,			// �A�OҪ���@ʾ��һ��
			fadeOutSpeed = 2000,	// �������ٶ�
			fadeInSpeed = 3000,		// ������ٶ�
			defaultZ = 10,			// �A�O�� z-index
			isHover = false,
			timer, speed = 2000;	// Ӌ�r����݆���ГQ���ٶ�
		
		// �Ȱ������DƬ��׃��͸��
		$ad.css({
			opacity: 0,
			zIndex: defaultZ - 1
		}).eq(showIndex).css({
			opacity: 1,
			zIndex: defaultZ
		});
		
		// �M�����µİ��o
		var str = '';
		for(var i=0;i<$ad.length;i++){
			str += '<a href="#">' + (i + 1) + '</a>';
		}
		var $controlA = $('#abgne_fade_pic').append($('<div class="control">' + str + '</div>').css('zIndex', defaultZ + 1)).find('.control a');

		// �����o���c�x�r
		// ��Ҫ׃�ɻ�������ГQ�r, ���԰� click �Q�� mouseover
		$controlA.click(function(){
			// ȡ��Ŀǰ�c����̖�a
			showIndex = $(this).text() * 1 - 1;
			
			// �@ʾ�������ą^��K�������^��׃��͸��
			$ad.eq(showIndex).stop().fadeTo(fadeInSpeed, 1, function(){
				if(!isHover){
					// ����Ӌ�r��
					timer = setTimeout(autoClick, speed);
				}
			}).css('zIndex', defaultZ).siblings('a').stop().fadeTo(fadeOutSpeed, 0).css('zIndex', defaultZ - 1);
			// ׌ a ���� .on
			$(this).addClass('on').siblings().removeClass('on');

			return false;
		}).focus(function(){
			$(this).blur();
		}).eq(showIndex).addClass('on');

		$ad.hover(function(){
			isHover = true;
			// ֹͣӋ�r��
			clearTimeout(timer);
		}, function(){
			isHover = false;
			// ����Ӌ�r��
			timer = setTimeout(autoClick, speed);
		})
		
		// �Ԅ��c����һ��
		function autoClick(){
			if(isHover) return;
			showIndex = (showIndex + 1) % $controlA.length;
			$controlA.eq(showIndex).click();
		}
		
		// ����Ӌ�r��
		timer = setTimeout(autoClick, speed);
	});
