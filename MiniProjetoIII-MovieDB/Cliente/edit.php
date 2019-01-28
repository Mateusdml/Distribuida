<?php
include_once 'forms/forms-handler.php';
include_once 'utils.php';

?>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Editar Filmes</title>

    <link rel="stylesheet" href="assets/bootstrap-material-design.min.css"/>
    <link rel="stylesheet" href="assets/movies.css"/>
</head>
<body>
    <script type="text/javascript" src="libs/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="libs/popper.min.js"></script>
    <script type="text/javascript" src="libs/bootstrap-material-design.js"></script>
    <script>$(document).ready(function () {
            $('body').bootstrapMaterialDesign();
        });</script>
    <script type="text/javascript" src="forms/submit-handler.js"></script>

    <div class="container">
        <div class="card">
            <div class="card-body">
                <h4 class="card-title">Editar filme</h4>
                <?php $temp = $movieService->findMovies($_GET["id"]);
                if (isset($temp["response"])) {
                    $movie = $temp["response"]; ?>

                    <form id="form-atualizar" method="post">
                        <input type="hidden" id="id" name="id" value="<?= $movie->id ?>">
                        <input type="hidden" id="base_url" name="base_url" value="<?= $base_url_location ?>">
                        <div class="form-group">
                            <label for="titulo" class="bmd-label-floating">Título</label>
                            <input type="text" class="form-control" id="titulo" name="titulo"
                                   value="<?= ucwords(strtolower($movie->titulo)) ?>">
                        </div>
                        <div class="form-group">
                            <label for="diretor" class="bmd-label-floating">Diretor</label>
                            <input type="text" class="form-control" id="diretor" name="diretor" disabled
                                   value="<?= ucwords(strtolower($movie->diretor)) ?>">
                        </div>
                        <div class="form-group">
                            <label for="estudio" class="bmd-label-floating">Estúdio</label>
                            <input type="text" class="form-control" id="estudio" name="estudio" disabled
                                   value="<?= ucwords(strtolower($movie->estudio)) ?>">
                        </div>
                        <div class="form-group">
                            <label for="genero" class="bmd-label-floating">Gênero</label>
                            <input type="text" class="form-control" id="genero" name="genero" disabled
                                   value="<?= ucwords(strtolower($movie->genero)) ?>">
                        </div>
                        <div class="form-group">
                            <label for="ano" class="bmd-label-floating">Ano de lançamento</label>
                            <input type="text" class="form-control" id="ano" name="ano" min="1930" max="2030" disabled
                                   value="<?= ucwords(strtolower($movie->lancamento)) ?>">
                        </div>
                    </form>

                    <input type="hidden" id="id" name="id" value="<?= $movie->id ?>"
                    <input type="hidden" id="titulo" name="titulo" value="<?= $movie->title ?>">
                    <input type="hidden" id="base_url" name="base_url" value="<?= $base_url_location ?>">

                    <div class="float-right">
                        <button class="btn btn-warning" id="excluir-filme" type="submit">Apagar</button>
                        <button class="btn btn-primary active" id="atualizar-filme" type="submit">Salvar</button>
                    </div>

                <?php } else if (isset($temp["error"])) { ?>
                    <div id="status-message"><?= $temp["error"] ?></div>
                <?php } ?>
                <div>
                    <a class="btn btn-default" href="<?= $base_url_location ?>/">Voltar</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>