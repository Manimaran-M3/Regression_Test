import whisper
import sys

if __name__ == "__main__":
    # Load the Whisper model (using the 'medium' model)
    model = whisper.load_model("medium")
    # Transcribe the audio file provided as an argument
    result = model.transcribe(sys.argv[1])
    # Output the transcription to standard output
    print(result["text"])
