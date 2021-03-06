package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.type.TypeFaction;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeBoolean;

public class CmdFactionsAccessFaction extends CmdFactionsAccessAbstract
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CmdFactionsAccessFaction()
	{
		// Aliases
		this.addAliases("faction");
		
		// Parameters
		this.addParameter(TypeFaction.get(), "faction");
		this.addParameter(TypeBoolean.getYes(), "yes/no", "toggle");

		// Requirements
		this.addRequirements(RequirementHasPerm.get(Perm.ACCESS_FACTION.node));
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void innerPerform() throws MassiveException
	{
		// Args
		Faction faction = this.readArg();
		boolean newValue = this.readArg(!ta.isFactionIdGranted(faction.getId()));
		
		// MPerm
		if (!MPerm.getPermAccess().has(msender, hostFaction, true)) return;
		
		// Apply
		ta = ta.withFactionId(faction.getId(), newValue);
		BoardColl.get().setTerritoryAccessAt(chunk, ta);
		
		// Inform
		this.sendAccessInfo();
	}
	
}
