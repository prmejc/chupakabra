$ ->
    $('#taskDate').datepicker({format: 'dd/mm/yyyy'})

    $('#taskDate').bind 'changeDate', (event) =>
            days = ["Ned", "Pon", "Tor", "Sre", "Čet", "Pet", "Sob", "Ned"]
            if not event.date
                event.date = new Date(event.timeStamp)
            $("#day_taskDate").html(days[event.date.getDay()])

    $("#select_device").select2()

