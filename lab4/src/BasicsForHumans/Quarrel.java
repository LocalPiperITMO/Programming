package BasicsForHumans;

public class Quarrel {
    private boolean active_quarrel;

    public Quarrel() {
        this.active_quarrel = false;
    }

    public class Side {
        private Human participant;
        private String argument;


        public Side(Human participant, String argument) {
            this.participant = participant;
            this.argument = argument;
        }

        public Human getParticipant() {
            return this.participant;
        }

        public void stateOpinion() {
            if (active_quarrel) {
                System.out.println(this.participant.getName() + " stated that he was the " + this.argument + ", therefore, opponent must respect his opinion");
            }

        }

    }

    public void quarrelStart(Side side1, Side side2) {
        System.out.println(side1.getParticipant().getName() + " and " + side2.getParticipant().getName() + " started arguing!");
        this.active_quarrel = true;
    }


    public void pacifyAttempt(Human pacifier) {
        if (active_quarrel) {
            if ((int) (Math.random() * 100) > 50) {
                System.out.println("Finally, " + pacifier.getName() + " stepped into the quarrel and ended it. Opponents stopped arguing");
                this.active_quarrel = false;
            } else {
                System.out.println(pacifier.getName() + " tried to stop the quarrel but failed");
            }
        }
    }
}