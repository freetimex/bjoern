package bjoern.input.radare.inputModule.creators;


import org.json.JSONArray;
import org.json.JSONObject;

import bjoern.input.common.structures.interpretations.BasicBlock;
import bjoern.input.common.structures.interpretations.Instruction;


public class RadareBasicBlockCreator
{

	public static BasicBlock createFromJSON(JSONObject block)
	{

		BasicBlock node = new BasicBlock();
		initFromJSON(node, block);
		return node;
	}

	public static void initFromJSON(BasicBlock node, JSONObject block)
	{
		long addr = block.getLong("offset");
		node.setAddr(addr);

		initInstructionsFromJSON(node, block);
	}

	private static void initInstructionsFromJSON(BasicBlock node,
			JSONObject block)
	{
		JSONArray instructionsJSON = block.getJSONArray("ops");

		int numberOfInstructions = instructionsJSON.length();
		for (int i = 0; i < numberOfInstructions; i++)
		{
			JSONObject jsonInstr = instructionsJSON.getJSONObject(i);
			Instruction instr = RadareInstructionCreator
					.createFromJSON(jsonInstr);
			node.addInstruction(instr);
		}
	}

}