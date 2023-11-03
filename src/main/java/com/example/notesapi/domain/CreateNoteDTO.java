package com.example.notesapi.domain;

public record CreateNoteDTO(Long userId, String title, String content) { }
