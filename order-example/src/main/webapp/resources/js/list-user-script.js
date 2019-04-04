var redirectToUser = function(){
    window.location = "user-controller";
}

$(window).on('load', function(){
    $(".js-novo-usuario").bind( "click", redirectToUser);
});


