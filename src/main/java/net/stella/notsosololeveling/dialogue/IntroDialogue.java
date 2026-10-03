package net.stella.notsosololeveling.dialogue;

import java.util.List;
import java.util.Map;

public final class IntroDialogue {

    public static final String START = "start";

    private static final Map<String, DialogueNode> NODES = Map.of(
            "start",
            new DialogueNode(
                    "Sung Jinwoo",
                    "Can you hear me?",
                    List.of(
                            new DialogueChoice(
                                    "Who are you?",
                                    "who_are_you"
                            ),
                            new DialogueChoice(
                                    "Where am I?",
                                    "where_am_i"
                            ),
                            new DialogueChoice(
                                    "I can hear you.",
                                    "explain_gates"
                            )
                    ),
                    false
            ),

            "who_are_you",
            new DialogueNode(
                    "Sung Jinwoo",
                    "My name is Sung Jinwoo. I'll explain what I can, but we don't have much time.",
                    List.of(
                            new DialogueChoice(
                                    "Continue",
                                    "explain_gates"
                            )
                    ),
                    false
            ),

            "where_am_i",
            new DialogueNode(
                    "Sung Jinwoo",
                    "A world beginning to show the same warning signs that appeared in mine.",
                    List.of(
                            new DialogueChoice(
                                    "What warning signs?",
                                    "explain_gates"
                            )
                    ),
                    false
            ),

            "explain_gates",
            new DialogueNode(
                    "Sung Jinwoo",
                    "Gates are beginning to appear. This world will need hunters capable of clearing them.",
                    List.of(
                            new DialogueChoice(
                                    "What do you need me to do?",
                                    "establish_colony"
                            ),
                            new DialogueChoice(
                                    "What happens if the Gates are ignored?",
                                    "gate_break"
                            )
                    ),
                    false
            ),

            "gate_break",
            new DialogueNode(
                    "Sung Jinwoo",
                    "They break. Whatever is trapped inside escapes, and the people nearby pay the price.",
                    List.of(
                            new DialogueChoice(
                                    "Then what do we do?",
                                    "establish_colony"
                            )
                    ),
                    false
            ),

            "establish_colony",
            new DialogueNode(
                    "Sung Jinwoo",
                    "Establish a settlement. Recruit people, identify those capable of Awakening, and prepare them to fight.",
                    List.of(
                            new DialogueChoice(
                                    "Understood",
                                    "complete"
                            )
                            ),
                    false
            ),

            "complete",
            new DialogueNode(
                    "Sung Jinwoo",
                    "Good. I'll speak with you again once you've established a base of operation. A Town Hall should work.",
                    List.of(),
                    true
            )
    );

    private IntroDialogue()
    {

    }

    public static DialogueNode getNode(String nodeId){
        DialogueNode node = NODES.get(nodeId);

        if (node == null){
            throw new IllegalArgumentException(
                    "Unknown intro dialogue node:" + nodeId
            );
        }

        return node;
    }

    public record DialogueNode(
            String speaker,
            String text,
            List<DialogueChoice> choices,
            boolean endsDialogue
    ){}

    public record DialogueChoice(
            String text,
            String nextNodeId
    ){}
}
