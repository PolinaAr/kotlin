package com.example.kotlin.specification

import com.example.kotlin.model.Client
import com.example.kotlin.model.Job
import com.example.kotlin.model.Position
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

class ClientSpecification {
    companion object {
        fun withPartialMatch(searchQuery: String): Specification<Client> {
            return Specification { root: Root<Client>, query: CriteriaQuery<*>, cb: CriteriaBuilder ->
                if (searchQuery.isNotBlank()) {
                    val searches = searchQuery.split(" ")
                    val predicates = searches.map { search ->
                        val likePattern = "%${search.lowercase()}%"
                        cb.or(
                            cb.like(cb.lower(root.get("firstName")), likePattern),
                            cb.like(cb.lower(root.get("lastName")), likePattern),
                            cb.like(cb.lower(root.get("email")), likePattern),
                            cb.like(cb.lower(root.join<Client, Job>("job").get("name")), likePattern),
                            cb.like(cb.lower(root.join<Client, Position>("position").get("name")), likePattern)
                        )
                    }.toTypedArray()

                    if (predicates.size > 1) {
                        query.where(cb.and(*predicates))
                    } else if (predicates.isNotEmpty()) {
                        query.where(predicates.first())
                    }
                }
                query.restriction
            }
        }

        fun withFieldMatch(fieldValues: Map<String, String>): Specification<Client> {
            return Specification { root: Root<Client>, _, cb: CriteriaBuilder ->
                val predicates = mutableListOf<Predicate>()
                fieldValues.forEach { (field, value) ->
                    val likePattern = "%$value%"
                    when (field) {
                        "firstName", "lastName", "email" -> predicates.add(
                            cb.like(
                                cb.lower(root.get(field)),
                                likePattern
                            )
                        )

                        "job" -> predicates.add(
                            cb.like(
                                cb.lower(root.join<Client, Job>("job").get("name")),
                                likePattern
                            )
                        )

                        "position" -> predicates.add(
                            cb.like(
                                cb.lower(
                                    root.join<Client, Position>("position").get("name")
                                ), likePattern
                            )
                        )
                    }
                }
                cb.and(*predicates.toTypedArray())
            }
        }
    }
}