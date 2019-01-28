<?php
include_once "MovieService.php";
$movieService = new MovieService();
$base_url_location = "/cliente";

$listOfMovies = $movieService->listMovies();