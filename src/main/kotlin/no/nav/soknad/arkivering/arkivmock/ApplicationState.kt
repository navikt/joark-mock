package no.nav.soknad.arkivering.arkivmock

data class ApplicationState(
	var alive: Boolean = true,
	var ready: Boolean = false
)