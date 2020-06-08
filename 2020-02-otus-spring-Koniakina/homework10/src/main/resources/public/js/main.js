function goToPage(url) {
    location.href = url;
}

function createItem(url, item, successUrl) {
    $.ajax({
        url: url,
        type: 'POST',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(item),
        dataType: "json",
        success: function(result) {
            goToPage(successUrl)
        },
        error: function(error) {
            alert(error.responseJSON.message)
        }
    })
}

function updateBook(url, item, successUrl) {
    $.ajax({
        url: url,
        type: 'PUT',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(item),
        dataType: "json",
        success: function (result) {
            goToPage(successUrl)
        },
        error: function (error) {
            alert(error.responseJSON.message);
        }
    })
}

function deleteItem(url, successUrl) {
    if (confirm($("#deleteText").text() + '?')) {
        $.ajax({
            url: url,
            type: 'DELETE',
            success: function (result) {
                goToPage(successUrl)
            },
            error: function (error) {
                alert(error.responseJSON.message)
            }
        })
    }
}

function getLocalizedText(type) {
    if (type === "edit")
        return $("#editText").text()
    if (type === "delete")
        return $("#deleteText").text()
}