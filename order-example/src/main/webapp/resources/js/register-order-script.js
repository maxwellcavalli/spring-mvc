
var tableConfig = {
    headers: ["Produto", "Quantidade", "Acoes" ],
    columnKeys: ["productName", "quantidade"],
    width: ["90%", "10%"],
    align: ["left", "right"]
}

var successLoadProducts = function (result,status,xhr){

    var container = $(".js-table-container");

    if (xhr.status == 200){
        var orderDeleted = eval($('#orderDeleted').val());
        console.log(orderDeleted);

        draw_a_table_from_json(result, tableConfig, "internalId", !orderDeleted, container);
    }
}

var removeProduct = function(id){
    var sendRemove = function (id){
        var data = {
            internalId: id
        }

         $.ajax({
              type: "POST",
              url: ctx.contextPath + '/order-controller/remove-product',
              data: JSON.stringify(data),
              success: successLoadProducts,
              dataType: "json",
              contentType: "application/json"
         });
    }

    openDialog('#confirm-dialog', function(){sendRemove(id)});
}


var loadProducts = function(){

    var data = {}

    $.ajax({
      type: "POST",
      url: ctx.contextPath + '/order-controller/load-products',
      data: JSON.stringify(data),
      success: successLoadProducts,
      dataType: "json",
      contentType: "application/json"
    });
}

var errorAddProduct = function (){
    openDialog('#message-product', null);
}

var addProduct = function(){
    var productId = $('.js-product option:selected').val();
    var productName = $('.js-product option:selected').text();
    var quantity = $('.js-quantidade').val();

    var data = {
        productId : productId,
        productName: productName,
        quantidade:  quantity
    }

    $.ajax({
      type: "POST",
      url: ctx.contextPath + '/order-controller/add-product',
      data: JSON.stringify(data),
      success: successLoadProducts,
      error: errorAddProduct,
      dataType: "json",
      contentType: "application/json"
    });
}

var afterDelete = function (){
    location.reload();
}

var errorOnDelete = function (){
    console.log('errorOnDelete');
}

var deleteOrder = function(){
    var sendRemoveOrder = function(){
        var id = $("#id_order").val();

        $.ajax({
              type: "POST",
              url: ctx.contextPath + '/order-controller/delete/'+id,
              data: {},
              success: afterDelete,
              error: errorOnDelete
        });
    }

    openDialog('#confirm-dialog', function(){sendRemoveOrder()});

}

$(window).on('load', function(){
    $(".js-adicionar-produto").bind( "click", addProduct);
    $(".js-delete-order").bind( "click", deleteOrder);

    loadProducts();
});


