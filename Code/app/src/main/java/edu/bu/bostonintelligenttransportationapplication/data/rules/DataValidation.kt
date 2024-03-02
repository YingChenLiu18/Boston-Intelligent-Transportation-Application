package edu.bu.bostonintelligenttransportationapplication.data.rules

import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object Validator {

    fun validFirstName(firstName : String) : ValidationResult{
        val letterCount = firstName.count { it.isLetter() }

        return ValidationResult(
            (!firstName.isNullOrEmpty() && letterCount >= 2)
        )
    }

    fun validLastName(lastName : String) : ValidationResult{
        return ValidationResult(
            (!lastName.isNullOrEmpty() && lastName.length >= 2 && lastName.all { it.isLetter() })
        )
    }

    fun validEmailAddress(emailAddress : String) : ValidationResult{
        if(emailAddress.isNullOrEmpty()){
            return ValidationResult(false)
        }

        val emailRegex = Regex("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")
        return ValidationResult(
            emailRegex.matches(emailAddress)
        )
    }

    fun validPassword(password : String) : ValidationResult{
        if(password.isNullOrEmpty()){
            return ValidationResult(false)
        }

        val hasUpperCase = Regex("[A-Z]").containsMatchIn(password)
        val hasLowerCase = Regex("[a-z]").containsMatchIn(password)
        val hasDigit = Regex("[0-9]").containsMatchIn(password)
        val hasSpecialChar = Regex("[.!@#$%^&*()_\\-+=<>?/]").containsMatchIn(password)

        return ValidationResult(
            password.length >= 6 && hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar
        )
    }

    fun validConfirmPassword(password : String, confirmPassword : String) : ValidationResult{
        if(confirmPassword.isNullOrEmpty()){
            return ValidationResult(false)
        }

        return ValidationResult(password == confirmPassword)
    }

    fun validRulesCheckBox(statusValue : Boolean) : ValidationResult{
        return ValidationResult(statusValue)
    }

    fun validCreditCard(cardNumber : String) : ValidationResult{
        return ValidationResult(
            (!cardNumber.isNullOrEmpty() && cardNumber.length == 16 && cardNumber.all { it.isDigit() })
        )
    }

    fun validCardHolder(cardholderName : String) : ValidationResult{
        val letterCount = cardholderName.count { it.isLetter() }
        val isValid = cardholderName.isNotEmpty() && letterCount >= 2
        return ValidationResult(isValid)
    }

    fun validExpirationDate(year : String, month : String) : ValidationResult{
        return try {
            val expiration = YearMonth.parse("$year-$month", DateTimeFormatter.ofPattern("yyyy-MM"))
            val isValid = !expiration.isBefore(YearMonth.now())

            ValidationResult(isValid)
        } catch (e: DateTimeParseException) {
            ValidationResult(false)
        }
    }

    fun validCvv(cvv : String) : ValidationResult{
        return ValidationResult(
            (!cvv.isNullOrEmpty() && (cvv.length == 3 || cvv.length == 4) && cvv.all { it.isDigit() })
        )
    }

}

data class ValidationResult(val status : Boolean)