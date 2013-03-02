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
			$('body').append "AJAX Error: #{jqXHR}"
		success: (data, textStatus, jqXHR) ->
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

$ ->


    intervalId = setInterval ( ->
      checkStatus()
    ), 1000