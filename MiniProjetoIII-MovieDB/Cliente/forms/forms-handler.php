<?php
include_once(__DIR__ . "/../utils.php");

if (sizeof($_POST) > 0 && isset($_POST["tipo"])) {
    switch ($_POST["tipo"]) {
        case "cadastrar":
            var_dump($movieService->cadastrarFilme($_POST["titulo"],
                $_POST["diretor"],
                $_POST["estudio"],
                $_POST["genero"],
                $_POST["lancamento"]));
            break;
        case "atualizar":
            if (isset($_POST["id"]) && isset($_POST["novoTitulo"])) {
                echo $movieService->updateMovie(
                    $_POST["id"],
                    $_POST["novoTitulo"]
                );
            } else {
                echo "[error] Não foi possível atualizar.";
            }
            break;
        case "apagar":
            if (isset($_POST) && isset($_POST["id"])) {
                $x = $movieService->deleteMovie($_POST["id"]);
                var_dump($x);
                echo $x;
            } else {
                echo "[error] Não foi possível apagar o filme selecionado.";
            };
            break;
        case "listar":
             foreach ($movieService->listMovies() as $singleMovie) {
                echo '<a class="list-group-item list-group-item-action" href='.$base_url_location.'/edit.php?id='.$singleMovie->id.'>'.$singleMovie->titulo.'</a>';
                echo "\n";
            }
            break;
        default:
            echo "[error]";
            break;
    }
}