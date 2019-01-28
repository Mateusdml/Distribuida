<?php
include 'utils.php';
include 'models/MovieResponse.php';

// Load SoapClient with URL on soap.ini
class MovieService
{

    protected $soapClient;

    function __construct()
    {
        $this->soapClient = new SoapClient(parse_ini_file("config/soap.ini")["wsdl_url"]);
    }

    function buildMovie($obj) {
    return new MovieResponse($obj["id"],
        $obj["titulo"],
        $obj["diretor"],
        $obj["estudio"],
        $obj["genero"],
        $obj["lancamento"]);
}

    function getSoapClient(): SoapClient {
        return $this->soapClient;
    }

    function listMovies(): array  {
        try {
            $resp = $this->soapClient->__soapCall("listarTodos", array());

            // caso venha só um, interpreta como objeto, mas deve responder array.
            if(isset($resp->return) && is_object($resp->return)) {
                return array(0 => $resp->return);
            }
            if (isset($resp->return) && is_array($resp->return)) {
                return $resp->return;
            } else {
                return array("error" => "Não foi possível trazer os filmes.");
            }
        } catch (SoapFault $fault) {
            echo "Pedido SOAP falhou: ( {$fault->faultstring} )";
            return array("erro" => "{$fault->faultstring}");
        }
    }

    function findMovies(string $text): array {
        try {
            // Faz a chamada ao ws
            $resp = $this->soapClient->__soapCall("buscarFilmes",
                array("parameters" => array("buscarPor" => $text))
            );

            if(isset($resp->return))
                return array("response"=>$resp->return);
            else {
                return array("error" => "Filme não encontrado.");
            }
        } catch (SoapFault $fault) {
            echo "[error] {$fault->faultstring}";
            return array("error" => $fault->faultstring);
        }
    }

    function cadastrarFilme(string $titulo,
                         string $diretor,
                         string $estudio,
                         string $genero,
                         string $lancamento): array {
        try {
            $resp = $this->soapClient->__soapCall('addFilme', array(
                    'parameters' => array("titulo" => $titulo,
                    "diretor" => $diretor,
                    "estudio" => $estudio,
                    "genero" => $genero,
                    "lancamento" => $lancamento))
            );
            var_dump($resp);
            echo $resp;
            return array("response"=>$resp);
        } catch (SoapFault $fault) {
            echo "[error] {$fault->faultstring}";
            return array("error" => $fault->faultstring);
        }
    }

    function updateMovie(string $id,
                         string $newTitle): array
    {
        try {
            $resp = $this->soapClient->
            __soapCall('atualizarFilme', array(
                    'parameters' => array("id" => $id, "novoTitulo" => $newTitle))
            );

            var_dump($resp);
            return array("response" => $resp);
        } catch (SoapFault $fault) {
            echo "[error] {$fault->faultstring}";
            return array("error" => $fault->faultstring);
        }
    }

    function deleteMovie(string $id) {
        try {
            $resp = $this->soapClient->__soapCall("apagarFilme", array(
                "parameters" => array("id" => $id))
            );

            return array("response" => $resp);
        } catch (SoapFault $fault) {
            echo "Pedido SOAP falhou: ( {$fault->faultstring} )";
        }
    }
}