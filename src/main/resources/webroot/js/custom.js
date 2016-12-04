$(function(){
	$('.selectable')
		.bind( 'touchstart', function(){
			$( this ).addClass( 'hover' );
	}).bind( 'touchend', function(){
		$( this ).removeClass( 'hover' );
	});

	$('input[name=ot]:radio').change(function() {
		if($(this).val() == 'limit') {
			$("#price").prop("disabled", false);
			$("#price").prop("required", true);
		} else if ($(this).val() == 'market') {
			$("#price").val('');
			$("#price").prop("disabled", true);
			$("#price").prop("required", false);
		}
	});
	
	$('.spinner .btn:first-of-type').on('click', function() {
		$('.spinner input').val( parseInt($('.spinner input').val(), 10) 
				+ parseInt($('.spinner input').attr('step'), 10));
	});

	$('.spinner .btn:last-of-type').on('click', function() {
		$('.spinner input').val( parseInt($('.spinner input').val(), 10) 
				- parseInt($('.spinner input').attr('step'), 10));
	});

});