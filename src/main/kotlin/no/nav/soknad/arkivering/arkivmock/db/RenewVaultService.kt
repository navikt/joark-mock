package no.nav.soknad.arkivering.arkivmock.db

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import no.nav.soknad.arkivering.arkivmock.ApplicationState
import org.slf4j.LoggerFactory

class RenewVaultService(private val vaultCredentialService: CredentialService) : RenewService {

	private val log = LoggerFactory.getLogger(javaClass)

	override fun startRenewTasks(applicationState: ApplicationState) {
		GlobalScope.launch {
			try {
				Vault.renewVaultTokenTask(applicationState)
			} catch (e: Exception) {
				log.error("Noe gikk galt ved fornying av vault-token", e.message)
				applicationState.ready = false
			}
		}

		GlobalScope.launch {
			try {
				vaultCredentialService.runRenewCredentialsTask(applicationState)
			} catch (e: Exception) {
				log.error("Noe gikk galt ved fornying av vault-credentials", e.message)
				applicationState.ready = false
			}
		}
	}
}
