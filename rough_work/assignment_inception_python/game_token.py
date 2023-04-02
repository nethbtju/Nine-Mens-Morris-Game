class GameToken:
    "A token in the game."

    def __init__(self, colour: str):
        self.colour = colour

    def get_colour(self) -> str:
        "Get the colour of the token."

        return self.colour
