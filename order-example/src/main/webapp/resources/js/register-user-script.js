var afterDelete = function (){
    location.replace('user-list-controller');
}

var errorOnDelete = function (){
    console.log('errorOnDelete');
}

var deleteUser = function(){
    var sendRemoveUser = function(){
        var id = $("#id_user").val();

        $.ajax({
              type: "POST",
              url: ctx.contextPath + '/user-controller/delete/'+id,
              data: {},
              success: afterDelete,
              error: errorOnDelete
        });
    }

    openDialog('#confirm-dialog', function(){sendRemoveUser()});

}

$(window).on('load', function(){
    $(".js-delete-user").bind( "click", deleteUser);
});


