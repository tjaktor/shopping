/**
 * Start eventsource. Connects to server which sends an emitter back.
 * TEST VERSION.
 */
function startEvents() {
	if (stream === undefined) {
		var stream = new EventSource("/event");

		stream.addEventListener('message', function(event) {
			console.log("ping");
			location.reload();
		});
	}
}

startEvents();