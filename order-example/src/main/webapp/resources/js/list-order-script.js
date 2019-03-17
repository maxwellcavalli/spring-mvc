var redirectToPedido = function(){
    window.location = "order-controller";
}

$(window).on('load', function(){
    $(".js-novo-pedido").bind( "click", redirectToPedido);
});


