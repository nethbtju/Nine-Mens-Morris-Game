from game_token import GameToken


class MillObserver:
    "An observer that checks for the formation of mills."

    def __init__(self):
        self.tokens: list[GameToken] = []

    def notify_push(self, token: GameToken) -> None:
        "Notify the observer of a push to the tokens it is watching."

        self.tokens.append(token)

    def notify_pop(self, token: GameToken):
        "Notify an observer of a pop from the tokens it is watching."

        if token in self.tokens:
            self.tokens.remove(token)

    def has_mill(self, colour: str) -> bool:
        "Check if a mill has been formed."

        token_counter = 0

        for token in self.tokens:
            if token.get_colour() == colour:
                token_counter += 1

        if token_counter == 3:
            return True

        return False

    def get_tokens(self) -> list[GameToken]:
        "Get the tokens that being observed by the MillObserver."

        return self.tokens
