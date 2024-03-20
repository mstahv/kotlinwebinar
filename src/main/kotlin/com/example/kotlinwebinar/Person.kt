package com.example.kotlinwebinar

import jakarta.persistence.*

@Entity
class Person(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val age: Integer,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?=null,
)
