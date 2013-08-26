$ ?= require 'jquery' # For Node.js compatibility

initListeners = () ->
    $(".switch").click ->
        data = $(this).attr('data')
        send2arduino(data)

send2arduino = (command) ->
	$.ajax "/command/#{command}",
		type: 'GET'
		dataType: 'html'
		cache: false
		error: (jqXHR, textStatus, errorThrown) ->
            console.log(textStatus)
            console.log(errorThrown)
            if jqXHR == null || jqXHR == ""
                console.log("nada")
		success: (data, textStatus, jqXHR) ->
            if data.indexOf("out") < 0
                console.log("NAPAKA: " + textStatus + " " + jqXHR + " " + data)
            else
                console.log data
                data = JSON.parse data
                for key, value of data.status.out
                    if value == 1
                        $("#" + key).removeClass 'btn-danger'
                        $("#" + key).addClass 'btn-success'
                        $("#" + key).attr "data", "write/#{key}-0"
                    else
                        $("#" + key).removeClass 'btn-success'
                        $("#" + key).addClass 'btn-danger'
                        $("#" + key).attr "data", "write/#{key}-1"
                for key, value of data.status.in
                    if key == "2"
                        if value == 1
                            $("#" + key).removeClass 'hide'
                        else
                            $("#" + key).addClass 'hide'
                    else
                        if value != 1
                            $("#" + key).removeClass 'alert'
                        else
                            $("#" + key).addClass 'alert'

checkStatus = () ->
    send2arduino("status/0")

intervalId = 0
intervalMs = 1000
$ ->
    initListeners()
    intervalId = setInterval ( ->
      checkStatus()
    ), intervalMs