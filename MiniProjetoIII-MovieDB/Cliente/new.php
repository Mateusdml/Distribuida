<?php
include_once 'forms/forms-handler.php';
include_once 'utils.php';

?>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Cadastrar novo filme</title>

    <link rel="stylesheet" href="assets/bootstrap-material-design.min.css"/>
    <link rel="stylesheet" href="assets/movies.css"/>
</head>
<body>
    <script type="text/javascript" src="libs/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="libs/popper.min.js"></script>
    <script type="text/javascript" src="libs/bootstrap-material-design.js"></script>
    <script>$(document).ready(function () {$('body').bootstrapMaterialDesign();});</script>
    <script type="text/javascript" src="forms/submit-handler.js"></script>

    <div class="container">
        <div class="card">
            <div class="card-body">
                <h4 class="card-title">Novo filme</h4>
                <form id="form-cadastrar" method="post">
                    <input type="hidden" name="tipo" id="tipo" value="cadastrar">
                    <input type="hidden" name="base_url" id="base_url" value="<?= $base_url_location ?>">
                    <div class="form-group">
                        <label class="bmd-label-floating" for="titulo">Título</label>
                        <input class="form-control" type="text" id="titulo" name="titulo"><br/>
                    </div>
                    <div class="form-group">
                        <label class="bmd-label-floating" for="diretor">Diretor</label>
                        <input class="form-control" type="text" id="diretor" name="diretor"><br/>
                    </div>
                    <div class="form-group">
                        <label class="bmd-label-floating" for="estudio">Estúdio</label>
                        <input class="form-control" type="text" id="estudio" name="estudio"><br/>
                    </div>
                    <div class="form-group">
                        <label class="bmd-label-floating" for="genero">Gênero</label>
                        <input class="form-control" type="text" id="genero" name="genero"><br/>
                    </div>
                    <div class="form-group">
                        <label class="bmd-label-floating" for="ano">Ano de lançamento</label>
                        <input class="form-control" type="text" id="lancamento" name="lancamento"><br/>
                    </div>
                    <div class="float-right">
                        <button class="btn btn-primary" id="cadastrar-filme" type="submit">Cadastrar</button>
                    </div>
                </form>

                <span id="status-message"></span>
                <a class="btn btn-default" href="<?= $base_url_location ?>/">Voltar</a>
            </div>
        </div>
    </div>
</body>
</html>
