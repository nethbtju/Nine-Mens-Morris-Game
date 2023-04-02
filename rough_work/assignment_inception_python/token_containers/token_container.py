from __future__ import annotations
from game_token import GameToken


class TokenContainer:
    """A bank of tokens used to place the initial tokens onto the rest of the
    board.
    """

    def __init__(
        self,
        location: str,
        maximum_tokens: int,
    ):
        self.location = location
        self.tokens: list[GameToken] = []
        self.maximum_tokens = maximum_tokens
        self.destination_containers: list[TokenContainer] = []

    def pop_token(self) -> GameToken:
        "Get a token from the container."

        return self.tokens.pop()

    def push_token(self, token: GameToken) -> None:
        "Push a token to the container."

        if len(self.tokens) < self.maximum_tokens:
            self.tokens.append(token)

    def peek_token(self) -> GameToken:
        "Look at the top token in the container without removing it."

        return self.tokens[-1]

    def is_full(self) -> bool:
        "Check if the container is full of tokens."

        if len(self.tokens) == self.maximum_tokens:
            return True
        return False

    def is_empty(self) -> bool:
        "Check if the container has no tokens remaining."

        if len(self.tokens) == 0:
            return True
        return False

    def get_location(self) -> str:
        "Get the location of the container."

        return self.location

    def add_destination_container(self, destination_container: TokenContainer) -> None:
        "Create a one way edge from this container to another."

        self.destination_containers.append(destination_container)

    def get_destination_containers(self) -> list[TokenContainer]:
        "Get a list of containers that this container is connected to."

        return self.destination_containers
