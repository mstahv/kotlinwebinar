package com.example.kotlinwebinar

import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<Person, Int> {
}