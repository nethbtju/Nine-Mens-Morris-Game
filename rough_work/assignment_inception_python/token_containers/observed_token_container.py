from game_token import GameToken
from mill_observer import MillObserver
from token_containers.token_container import TokenContainer


class ObservedTokenContainer(TokenContainer):
    "A special token container with support for observers."

    def __init__(
        self,
        location: str,
        maximum_tokens: int,
    ):
        super().__init__(location, maximum_tokens)
        self.observers: list[MillObserver] = []

    def attach_observer(self, observer: MillObserver):
        "Attach an observer to the intersection."

        self.observers.append(observer)

    def pop_token(self) -> GameToken:
        "Get a token from the container."

        token = super().pop_token()
        for observer in self.observers:
            observer.notify_pop(token)
        return token

    def push_token(self, token: GameToken) -> None:
        "Push a token to the container."

        super().push_token(token)
        for observer in self.observers:
            observer.notify_push(token)
