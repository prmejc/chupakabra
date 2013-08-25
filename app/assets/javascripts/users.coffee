

$ ->
    $('.addUser').bind 'click', ->
        $("#addUserDialog").modal('show')

    $('.changePass').bind 'click', ->
        console.log $(this).attr('data')
        $("#userToChange").attr('value', $(this).attr('data'))
        $("#changePasswordDialog").modal('show')


    $('.deleteUser').bind 'click', ->
        userName = $(this).attr('data')
        jsRoutes.controllers.UserController.deleteUser(userName).ajax
                            context: this
                            success: (data) ->
                                console.log "izbrisan"
                                window.location.reload(true);
                            error: (err) ->
                                console.log "Prišlo je do napake" + err


    $('#addUserBtn').bind 'click', ->
            $.post(
                $("#userForm").attr('action')
                $("#userForm").serialize()
                (data, textStatus, jqXHR) ->
                    $("#addUserDialog").modal('hide')
                    window.location.reload(true);
            )
            return false

    $('#changePassBtn').bind 'click', ->
            $.post(
                $("#passForm").attr('action')
                $("#passForm").serialize()
                (data, textStatus, jqXHR) ->
                    $("#changePasswordDialog").modal('hide')
                    window.location.reload(true);
            )
            return false