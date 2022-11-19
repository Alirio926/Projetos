
$(document).ready(function(){
	$('#btn-pesquisa').click(function(){
		var monthData = escape($('#txt-pesquisa').val());
		$.ajax({
			method: 'GET',
			url: '/pages/pedido/listarByMonth?month=' + monthData,
			success: function(data){
				console.log(data);
			},
			error: function(){
				alert("Houve um erro na requisição.\n"+request.responseText);
			}
		});
	});
});