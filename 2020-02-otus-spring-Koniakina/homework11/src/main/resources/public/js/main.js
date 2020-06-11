function goToPage(url) {
    location.href = url;
}

function createItem(url, item) {
    $.ajax({
        url: url,
        type: 'POST',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(item),
        dataType: "json",
        error: function(error) {
            handleError(error);
        }
    })
}

function updateBook(item) {
    $.ajax('/api/book',{
        type: 'PUT',
        data: JSON.stringify(item),
        dataType: "json",
        error: function(jqXHR) {
            handleError(jqXHR);
        },
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
}

function deleteItem(id) {
    if (confirm('Вы уверены, что хотите удалить книгу ' + id + ' ?'))  {
        $.ajax(`/api/book/${id}`, {
            type: 'DELETE',
            error: function (jqXHR) {
                handleError(jqXHR);
            }
        })
    }
}

function loadBooksList(books) {
    $('tbody').children().remove();
    books.forEach(function (book) {
        $('tbody').append(`
                     <tr>
                            <td>${book.id}</td>
                            <td>${book.title}</td>
                            <td>${book.author.fullName}</td>
                            <td>${book.genre.title}</td>
                            <td>
                                <a type="button" class="btn btn-primary btn-sm" href="/edit.html?id=${book.id}">Редактировать</a>
                            </td>
                            <td>
                                <a type="button" class="btn btn-secondary btn-sm deleteBtn" href="" id="${book.id}">Удалить</a>
                            </td>
                        </tr>
                    `)
    });
    $('.deleteBtn').on('click', function () {
        deleteItem(this.id);
        loadBooksList();
        return false;
    });
}

function handleError(jqXHR) {
    alert(`${jqXHR.status}. ${jqXHR.responseJSON.message}`)
}

