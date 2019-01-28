$(document).ready(function () {
    $('#cadastrar-filme').on('click', function (ev) {
        ev.preventDefault();
        var tipo = $('input[name="tipo"]').val();
        var titulo = $('input[name="titulo"]').val();
        var diretor = $('input[name="diretor"]').val();
        var estudio = $('input[name="estudio"]').val();
        var genero = $('input[name="genero"]').val();
        var lancamento = $('input[name="lancamento"]').val();

        if (titulo === '' || diretor === '' || estudio === '' || genero === '' || lancamento === '') {
            alert("Favor preencher os campos corretamente.");
        } else if (lancamento < 1900 || lancamento > 2030) {
            alert("Informe um ano válido (entre 1900 e 2030)");
        } else {
            $.post("forms/forms-handler.php", {
                tipo: 'cadastrar',
                titulo: titulo,
                diretor: diretor,
                estudio: estudio,
                genero: genero,
                lancamento: lancamento,
            }, function (str) {
                if (str.includes("[error]")) {
                    alert("Ocorreu um erro e não foi possível cadastrar o filme:<br/>" + str.split("[error]")[1]);
                } else {
                    alert("Filme \"" + titulo + "\" cadastrado com sucesso!");
                    $('#form-cadastrar')[0].reset();
                    window.location.assign($('#base_url').val());
                }
            });
        }
    });

    function responseHandler(string, successful) {
        if (string.contains("[error]")) {
            alert("Ocorreu um erro: " + string);
        } else {
            successful(string);
        }
    }

    $('#atualizar-filme').on('click', function (ev) {
        ev.preventDefault();
        var id = $('input[name="id"]').val();
        var titulo = $('input[name="titulo"]').val();

        if (id === '' || titulo === '') {
            alert("Favor preencher os campos corretamente.");
        } else {
            $.post("forms/forms-handler.php", {
                tipo: "atualizar",
                id: id,
                novoTitulo: titulo,
            }, function (str) {
                if (str.includes("[error] ")) {
                    alert("Ocorreu um erro: " + str.split("[error] ")[1]);
                    $('#form-atualizar')[0].reset();
                } else {
                    alert("Filme atualizado com sucesso!");
                    console.log("RESPOSTA atualização: ", str);

                    $('#form-atualizar')[0].reset();
                    window.location.replace($('input[name="base_url"]').val() + "/edit.php?id=" + id);
                }
            });
        }
    });

    $('#excluir-filme').on('click', function (ev) {
        ev.preventDefault();
        var id = $('input[name="id"]').val();
        var titulo = $('input[name="titulo"]').val();

        if (id === '' || titulo === '') {
            alert("Favor preencher os campos corretamente.");
        } else {
            if (confirm("Excluir o filme \"" + titulo + "\"?")) {
                $.post("forms/forms-handler.php", {
                    tipo: "apagar",
                    id: id
                }, function (str) {
                    if (str.includes("[error] ")) {
                        alert("Ocorreu um erro: " + str.split("[error] ")[1]);
                    } else {
                        alert("Filme excluido com sucesso!\n");
                        console.log("RESPOSTA EXCLUSÃO:", str);
                        window.location.assign($('input[name="base_url"]').val());
                    }
                });
            }
        }
    });

    $('#atualizar-lista').on('click', function (ev) {
        ev.preventDefault();
        $('#lista-filmes').empty();
        $.post("forms/forms-handler.php", {
            tipo: "listar"
        }, function (resp) {
            $('#lista-filmes').html(resp);
        });
    })
});