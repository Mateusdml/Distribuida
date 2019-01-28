<?php
include_once 'forms/forms-handler.php';
include_once 'utils.php';
?>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Biblioteca de Filmes</title>

    <link rel="stylesheet" href="assets/bootstrap-material-design.min.css" />
    <link rel="stylesheet" href="assets/movies.css" />
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
                <h4 class="card-title">Listagem de filmes</h4>
                <div id="lista-filmes" class="list-group">
                    <?php foreach ($listOfMovies as $singleMovie) { ?>
                        <a class="list-group-item list-group-item-action"
                           href="<?= $base_url_location ?>/edit.php?id=<?= $singleMovie->id ?>"><?= $singleMovie->titulo ?></a>
                    <?php } ?>
                </div>

                <div class="card-body">
                    <button class="btn btn-secondary" id="atualizar-lista">Atualizar</button>
                    <button class="btn btn-primary " id="novo-filme" type="button"
                            onclick="window.location.assign('<?= $base_url_location ?>/new.php')">
                        Cadastrar
                    </button>
                </div>
            </div>
        </div>
    </div>

</body>
</html>