public enum Subject {
    P {
        @Override
        public String subjectName() {
            return "Programmazione";
        }

        @Override
        public int capacity() {
            return 4;
        }
    },
    SO {
        @Override
        public String subjectName() {
            return "Sistemi Operativi";
        }

        @Override
        public int capacity() {
            return 3;
        }
    },
    R {
        @Override
        public String subjectName() {
            return "Reti";
        }

        @Override
        public int capacity() {
            return 2;
        }
    };

    abstract public String subjectName();
    abstract public int capacity();
}
