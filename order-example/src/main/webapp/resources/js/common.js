var ctx = {
    contextPath : ""
}

function draw_a_table_from_json(json_data_array, tableConfig, keyCollumn, drawActionButton, destination_element) {
    $(destination_element).empty();

    var table = '<table>';
    //TH Loop
    table += '<tr class="table-header">';
    $.each(tableConfig.headers, function (head_array_key, head_array_value) {
        table += '<th>' + head_array_value + '</th>';
    });
    table += '</tr>';

    //TR loop
    $.each(json_data_array, function (key, value) {
        var map = new Map(Object.entries(value));

        table += '<tr>';
        //TD loop
        $.each(tableConfig.columnKeys, function (item_key, item_value) {
            table += '<td '+
                ' width="' +tableConfig.width[item_key] +'"'+
                ' align="'+tableConfig.align[item_key]+'">' + map.get(item_value) + '</td>';
        });

        console.log('drawActionButton = ' + drawActionButton);

        if (drawActionButton){
            table += '<td><button type="button" onclick="removeProduct('+map.get(keyCollumn)+')" >Remover</button></td>';
        } else {
            table += '<td> </td>';
        }
        table += '</tr>';
    });
    table += '</table>';

    $(destination_element).append(table);
}


$( function() {
    $( "#confirm-dialog" ).dialog({
      modal: true,
      autoOpen: false,
      buttons: {
        Ok: function() {
            $( this ).dialog( "close" );
        }
      }
    });
} );

var initializeDialog = function (dialogSelector){
    $( dialogSelector ).dialog({
      modal: true,
      autoOpen: false,
      buttons: {
        Ok: function() {
            $( this ).dialog( "close" );
        }
      }
    });

}

var openDialog = function(dialogSelector, callback){

    if (callback !== null){
        $( dialogSelector ).dialog( "option", "buttons",
             [
                {
                    text: "Ok",
                    //icon: "ui-icon-heart",
                    click: function() {
                        callback.call();
                        $( this ).dialog( "close" );
                    }
                },
                {
                    text: "Cancel",
                    //icon: "ui-icon-heart",
                    click: function() {
                        $( this ).dialog( "close" );
                    }
                }

             ]
         );
    }

    $( dialogSelector ).dialog('open');
}