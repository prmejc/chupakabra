$ ?= require 'jquery' # For Node.js compatibility

initListeners = () ->
    $(".switch").click ->
        id_val = $(this).attr('value')
        data = $(this).attr('data')
        send2arduino(data)



send2arduino = (command) ->
	$.ajax "/command/#{command}",
		type: 'GET'
		dataType: 'html'
		error: (jqXHR, textStatus, errorThrown) ->
            console.log(textStatus)
            console.log(errorThrown)
            if jqXHR == null || jqXHR == ""
                console.log("nada")
            $('#out_panel').html("<div class='alert alert-error'><button type='button' class='close' data-dismiss='alert'>&times;</button><h4>Napaka!</h4>Nadzorna plošča je nedosegljiva.</div>")
            $('#in_panel').html("")
		success: (data, textStatus, jqXHR) ->
            if data.indexOf("out") < 0
                alert data
            else
                data = JSON.parse data
                $('#out_panel').html("")
                $('#in_panel').html("")
                for key, value of data.status.out
                    if value == 1
                        $('#out_panel').append "<input type='button' class='btn btn-success switch' value='#{key}' data='write/#{key}-0'>"
                    else
                        $('#out_panel').append "<input type='button' class='btn btn-danger switch' value='#{key}' data='write/#{key}-1'>"
                for key, value of data.status.in
                    if value == 1
                        $('#in_panel').append "<span class='badge badge-success'>#{key}</span>"
                    else
                        $('#in_panel').append "<span class='badge badge-important'>#{key}</span>"
                initListeners()

checkStatus = () ->
    send2arduino("status/0")


intervalId = 0
intervalMs = 1000


$("#intervalBtn").click ->
        clearInterval(intervalId)
        intervalMs = parseInt($("#intervalInput").val(), 10)
        alert "Osvežujem na #{intervalMs} ms"
        intervalId = setInterval ( ->
              checkStatus()
            ), intervalMs

$ ->


    intervalId = setInterval ( ->
      checkStatus()
    ), intervalMs