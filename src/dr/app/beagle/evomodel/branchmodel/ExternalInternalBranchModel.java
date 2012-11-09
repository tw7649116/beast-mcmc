package dr.app.beagle.evomodel.branchmodel;

import dr.app.beagle.evomodel.substmodel.SubstitutionModel;
import dr.evolution.tree.NodeRef;
import dr.evomodel.tree.TreeModel;
import dr.inference.model.AbstractModel;
import dr.inference.model.Model;
import dr.inference.model.Variable;
import dr.util.Author;
import dr.util.Citable;
import dr.util.Citation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Rambaut
 * @author Filip Bielejec
 * @version $Id$
 */
public class ExternalInternalBranchModel extends AbstractModel implements BranchModel, Citable {
    public ExternalInternalBranchModel(TreeModel tree, SubstitutionModel externalSubstModel, SubstitutionModel internalSubstModel) {
        super("ExternalInternalBranchModel");

        this.tree = tree;
        this.externalSubstModel = externalSubstModel;
        this.internalSubstModel = internalSubstModel;

        addModel(tree);
        addModel(externalSubstModel);
        addModel(externalSubstModel);
    }

    public Mapping getBranchModelMapping(final NodeRef node) {
        return new Mapping() {
            public int[] getOrder() {
                return new int[] { tree.isExternal(node) ? 0 : 1 };
            }

            public double[] getWeights() {
                return new double[] { 1.0 };
            }
        };
    }

    @Override
    public List<SubstitutionModel> getSubstitutionModels() {
        List<SubstitutionModel> substitutionModels = new ArrayList<SubstitutionModel>();
        substitutionModels.add(externalSubstModel);
        substitutionModels.add(internalSubstModel);
        return substitutionModels;
    }

    @Override
    public boolean requiresMatrixConvolution() {
        return false;
    }


    @Override
    protected void handleModelChangedEvent(Model model, Object object, int index) {
        fireModelChanged();
    }

    @Override
    protected void handleVariableChangedEvent(Variable variable, int index, Variable.ChangeType type) {
    }

    @Override
    protected void storeState() {
    }

    @Override
    protected void restoreState() {
    }

    @Override
    protected void acceptState() {
    }

    /**
     * @return a list of citations associated with this object
     */
    public List<Citation> getCitations() {
        List<Citation> citations = new ArrayList<Citation>();
        citations.add(
                new Citation(
                        new Author[]{
                                new Author("F", "Bielejec"),
                                new Author("P", "Lemey"),
                                new Author("A", "Rambaut"),
                                new Author("MA", "Suchard")
                        },
                        Citation.Status.IN_PREPARATION
                )
        );
        return citations;
    }

    private final TreeModel tree;
    private final SubstitutionModel externalSubstModel;
    private final SubstitutionModel internalSubstModel;
}