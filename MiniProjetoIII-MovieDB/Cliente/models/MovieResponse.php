<?php

class MovieResponse
{
    private $id;
    private $title;
    private $director;
    private $studio;
    private $genre;
    private $launchYear;

    public function __construct($id, $title, $director, $studio, $genre, $launchYear)
    {
        $this->director = $director;
        $this->genre = $genre;
        $this->id = $id;
        $this->launchYear = $launchYear;
        $this->studio = $studio;
        $this->title = $title;
    }

    public function getDirector()
    {
        return $this->director;
    }

    public function getGenre()
    {
        return $this->genre;
    }

    public function getId()
    {
        return $this->id;
    }

    public function getLaunchYear()
    {
        return $this->launchYear;
    }

    public function getStudio()
    {
        return $this->studio;
    }

    public function getTitle()
    {
        return $this->title;
    }
}
