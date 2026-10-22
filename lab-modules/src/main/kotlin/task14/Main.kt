package task14

sealed interface Outcome<out T> {
    data class Ok<T>(val value: T) : Outcome<T>
    data class Fail(val reason: String) : Outcome<Nothing>
}

fun parseQuantity(text: String): Outcome<Int> {
    val number = text.trim().toIntOrNull()
        ?: return Outcome.Fail("'$text' is not a whole number")
    if (number <= 0) return Outcome.Fail("The quantity must be positive")
    if (number > 100) return Outcome.Fail("We cannot sell more than 100 at once")
    return Outcome.Ok(number)
}

fun parsePrice(text: String): Outcome<Double> {
    val price = text.trim().toDoubleOrNull()
        ?: return Outcome.Fail("'$text' is not a price")
    if (price <= 0.0) return Outcome.Fail("The price must be positive")
    return Outcome.Ok(price)
}

fun parseEmail(text: String): Outcome<String> {
    val email = text.trim()
    if ('@' !in email) return Outcome.Fail("Email must contain '@'")
    return Outcome.Ok(email)
}

fun main() {
    print("Quantity: ")
    val qtyOutcome = parseQuantity(readln())
    if (qtyOutcome is Outcome.Fail) {
        println("Quantity error: ${qtyOutcome.reason}")
        return
    }

    print("Unit price: ")
    val priceOutcome = parsePrice(readln())
    if (priceOutcome is Outcome.Fail) {
        println("Price error: ${priceOutcome.reason}")
        return
    }

    print("Email: ")
    val emailOutcome = parseEmail(readln())
    if (emailOutcome is Outcome.Fail) {
        println("Email error: ${emailOutcome.reason}")
        return
    }

    val quantity = (qtyOutcome as Outcome.Ok).value
    val price = (priceOutcome as Outcome.Ok).value
    val email = (emailOutcome as Outcome.Ok).value

    println("Total: ${quantity * price} UZS (Receipt sent to $email)")
}
